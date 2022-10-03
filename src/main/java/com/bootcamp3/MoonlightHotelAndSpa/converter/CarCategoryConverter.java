package com.bootcamp3.MoonlightHotelAndSpa.converter;

import com.bootcamp3.MoonlightHotelAndSpa.dto.transfer.CarCategoryRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.transfer.CarCategoryResponse;
import com.bootcamp3.MoonlightHotelAndSpa.model.car.CarCategory;

public class CarCategoryConverter {

    public static CarCategory convertToCarCategory(CarCategoryRequest request) {

        CarCategory carCategory = new CarCategory();
        carCategory.setTitle(request.getTitle());
        carCategory.setSeats(request.getSeats());
        carCategory.setPrice(request.getPrice());

        return carCategory;
    }

    public static CarCategoryResponse convertToCarCategoryResponse(CarCategory carCategory) {

        CarCategoryResponse response = new CarCategoryResponse();
        response.setId(carCategory.getId());
        response.setTitle(carCategory.getTitle());
        response.setSeats(carCategory.getSeats());
        response.setPrice(carCategory.getPrice());

        return response;
    }
}
