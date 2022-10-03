package com.bootcamp3.MoonlightHotelAndSpa.service;

import com.bootcamp3.MoonlightHotelAndSpa.dto.transfer.CarCategoryRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.transfer.CarCategoryResponse;
import com.bootcamp3.MoonlightHotelAndSpa.model.car.CarCategory;

public interface CarCategoryService {

    CarCategoryResponse createCarCategory(CarCategoryRequest request);

    CarCategory findCategoryById(Long id);
}
