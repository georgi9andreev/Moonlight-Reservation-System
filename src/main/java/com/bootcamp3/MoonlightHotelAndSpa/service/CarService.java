package com.bootcamp3.MoonlightHotelAndSpa.service;

import com.bootcamp3.MoonlightHotelAndSpa.dto.transfer.CarRequest;
import com.bootcamp3.MoonlightHotelAndSpa.model.car.Car;

public interface CarService {

    void createCar(Car car);

    void deleteCarById(Long id);

    Car updateCarById(Long id, CarRequest carRequest);

    Car findCarById(Long id);
}
