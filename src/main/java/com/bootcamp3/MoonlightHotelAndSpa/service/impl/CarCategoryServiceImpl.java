package com.bootcamp3.MoonlightHotelAndSpa.service.impl;

import com.bootcamp3.MoonlightHotelAndSpa.converter.CarCategoryConverter;
import com.bootcamp3.MoonlightHotelAndSpa.dto.transfer.CarCategoryRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.transfer.CarCategoryResponse;
import com.bootcamp3.MoonlightHotelAndSpa.exception.RecordNotFoudException;
import com.bootcamp3.MoonlightHotelAndSpa.model.car.CarCategory;
import com.bootcamp3.MoonlightHotelAndSpa.repository.CarCategoryRepository;
import com.bootcamp3.MoonlightHotelAndSpa.service.CarCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CarCategoryServiceImpl implements CarCategoryService {

    private final CarCategoryRepository carCategoryRepository;

    @Autowired
    public CarCategoryServiceImpl(CarCategoryRepository carCategoryRepository) {
        this.carCategoryRepository = carCategoryRepository;
    }

    @Override
    public CarCategoryResponse createCarCategory(CarCategoryRequest request) {

        CarCategory carCategory = CarCategoryConverter.convertToCarCategory(request);
        carCategoryRepository.save(Objects.requireNonNull(carCategory));

        return CarCategoryConverter.convertToCarCategoryResponse(carCategory);
    }

    @Override
    public CarCategory findCategoryById(Long id) {
        return carCategoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoudException("Category not found"));
    }
}
