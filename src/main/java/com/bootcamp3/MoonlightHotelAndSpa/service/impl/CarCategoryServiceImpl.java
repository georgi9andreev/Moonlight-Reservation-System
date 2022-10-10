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
    public void createCarCategory(CarCategory carCategory) {

        carCategoryRepository.save(carCategory);
    }

    @Override
    public CarCategory findCategoryById(Long id) {
        return carCategoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoudException("Category not found"));
    }

    @Override
    public void deleteCarCategoryById(Long id) {
        carCategoryRepository.deleteById(id);
    }

    @Override
    public CarCategory updateCarCategory(Long id, CarCategoryRequest carCategoryRequest) {

        CarCategory foundCarCategory = findCategoryById(id);

        CarCategory updatedCarCategory = CarCategoryConverter.update(foundCarCategory, carCategoryRequest);
        createCarCategory(updatedCarCategory);

        return updatedCarCategory;
    }
}
