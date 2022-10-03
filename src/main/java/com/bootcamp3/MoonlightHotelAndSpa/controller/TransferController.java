package com.bootcamp3.MoonlightHotelAndSpa.controller;

import com.bootcamp3.MoonlightHotelAndSpa.converter.CarConverter;
import com.bootcamp3.MoonlightHotelAndSpa.dto.transfer.CarCategoryRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.transfer.CarCategoryResponse;
import com.bootcamp3.MoonlightHotelAndSpa.dto.transfer.CarRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.transfer.CarResponse;
import com.bootcamp3.MoonlightHotelAndSpa.model.car.Car;
import com.bootcamp3.MoonlightHotelAndSpa.service.CarCategoryService;
import com.bootcamp3.MoonlightHotelAndSpa.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "cars")
public class TransferController {

    private final CarCategoryService carCategoryService;
    private final CarService carService;

    @Autowired
    public TransferController(CarCategoryService carCategoryService, CarService carService) {
        this.carCategoryService = carCategoryService;
        this.carService = carService;
    }

    @PostMapping(value = "categories")
    public ResponseEntity<CarCategoryResponse> createCarCategory(@RequestBody CarCategoryRequest request) {

        CarCategoryResponse response = carCategoryService.createCarCategory(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CarResponse> createCar(@RequestBody CarRequest request) {

        Car car = CarConverter.convertToCar(request);
        carService.createCar(car);

        CarResponse carResponse = CarConverter.convertToCarResponse(car);
        return new ResponseEntity<>(carResponse, HttpStatus.OK);
    }
}
