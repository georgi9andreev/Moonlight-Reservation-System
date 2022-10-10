package com.bootcamp3.MoonlightHotelAndSpa.service;

import com.bootcamp3.MoonlightHotelAndSpa.dto.transfer.CarCategoryRequest;
import com.bootcamp3.MoonlightHotelAndSpa.model.car.CarCategory;

public interface CarCategoryService {

    void createCarCategory(CarCategory carCategory);

    CarCategory findCategoryById(Long id);

    void deleteCarCategoryById(Long id);

    CarCategory updateCarCategory(Long id, CarCategoryRequest carCategoryRequest);
}
