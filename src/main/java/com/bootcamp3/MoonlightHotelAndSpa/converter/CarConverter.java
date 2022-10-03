package com.bootcamp3.MoonlightHotelAndSpa.converter;

import com.bootcamp3.MoonlightHotelAndSpa.dto.transfer.CarRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.transfer.CarResponse;
import com.bootcamp3.MoonlightHotelAndSpa.model.Image;
import com.bootcamp3.MoonlightHotelAndSpa.model.car.Car;
import com.bootcamp3.MoonlightHotelAndSpa.model.car.CarCategory;
import com.bootcamp3.MoonlightHotelAndSpa.service.CarCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CarConverter {

    private static CarCategoryService carCategoryService;

    @Autowired
    public CarConverter(CarCategoryService carCategoryService) {

        CarConverter.carCategoryService = carCategoryService;
    }

    public static Car convertToCar(CarRequest carRequest) {

        CarCategory carCategory = carCategoryService.findCategoryById(carRequest.getCategory());

        Car car = new Car();
        car.setCategory(carCategory);
        car.setBrand(carRequest.getBrand());
        car.setModel(carRequest.getModel());
        car.setImage(carRequest.getImage());
        car.setImages(getImagesFromRequest(carRequest));
        car.setYear(carRequest.getYear());
        car.setCreated(Instant.now().toString());

        return car;
    }

    public static CarResponse convertToCarResponse(Car car) {

        CarResponse carResponse = new CarResponse();
        carResponse.setId(car.getId());
        carResponse.setBrand(car.getBrand());
        carResponse.setModel(car.getModel());
        carResponse.setImage(car.getImage());
        carResponse.setImages(getImagesFromCars(car));
        carResponse.setYear(car.getYear());
        carResponse.setCreated(car.getCreated());
        carResponse.setCategory(CarCategoryConverter.convertToCarCategoryResponse(car.getCategory()));

        return carResponse;
    }

    private static Set<Image> getImagesFromRequest(CarRequest request) {

        return request
                .getImages()
                .stream()
                .map(image -> Image.builder().url(image).build())
                .collect(Collectors.toSet());
    }

    private static Set<String> getImagesFromCars(Car car) {

        return car
                .getImages()
                .stream()
                .map(Image::getUrl)
                .collect(Collectors.toSet());
    }
}
