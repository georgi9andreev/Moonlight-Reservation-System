package com.bootcamp3.MoonlightHotelAndSpa.service.impl;

import com.bootcamp3.MoonlightHotelAndSpa.converter.CarConverter;
import com.bootcamp3.MoonlightHotelAndSpa.dto.transfer.CarRequest;
import com.bootcamp3.MoonlightHotelAndSpa.exception.RecordNotFoudException;
import com.bootcamp3.MoonlightHotelAndSpa.model.car.Car;
import com.bootcamp3.MoonlightHotelAndSpa.repository.CarRepository;
import com.bootcamp3.MoonlightHotelAndSpa.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void createCar(Car car) {

        carRepository.save(car);
    }

    @Override
    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public Car updateCarById(Long id, CarRequest carRequest) {

        Car car = findCarById(id);

        Car updatedCar = CarConverter.update(car, carRequest);
        createCar(updatedCar);

        return updatedCar;
    }

    @Override
    public Car findCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoudException(String.format("Car with id: %d, not found", id)));
    }

    @Override
    public List<Car> getAvailableCars(int seats, Instant date) {

        return carRepository.findCarsBySeatsAndCarTransferDate(seats, date);
    }
}
