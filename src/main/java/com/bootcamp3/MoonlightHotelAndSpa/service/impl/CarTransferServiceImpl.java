package com.bootcamp3.MoonlightHotelAndSpa.service.impl;

import com.bootcamp3.MoonlightHotelAndSpa.converter.CarTransferConverter;
import com.bootcamp3.MoonlightHotelAndSpa.dto.transfer.CarTransferRequest;
import com.bootcamp3.MoonlightHotelAndSpa.exception.RecordNotFoudException;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import com.bootcamp3.MoonlightHotelAndSpa.model.car.Car;
import com.bootcamp3.MoonlightHotelAndSpa.model.car.CarTransfer;
import com.bootcamp3.MoonlightHotelAndSpa.repository.CarTransferRepository;
import com.bootcamp3.MoonlightHotelAndSpa.service.CarService;
import com.bootcamp3.MoonlightHotelAndSpa.service.CarTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarTransferServiceImpl implements CarTransferService {

    private final CarTransferRepository carTransferRepository;
    private final CarService carService;

    @Autowired
    public CarTransferServiceImpl(CarTransferRepository carTransferRepository, CarService carService) {
        this.carTransferRepository = carTransferRepository;
        this.carService = carService;
    }

    @Override
    public CarTransfer createCarTransfer(Long id, CarTransferRequest carTransferRequest, User user) {

        CarTransfer carTransfer = CarTransferConverter.convertToCarTransfer(id, carTransferRequest, user);
        carTransferRepository.save(carTransfer);

        return carTransfer;
    }

    @Override
    public List<CarTransfer> getAllTransfersByCarId(Long id) {

        Car foundCar = carService.findCarById(id);

        return foundCar.getCarTransfers();
    }

    @Override
    public List<CarTransfer> getAllTransfers() {
        return carTransferRepository.findAll();
    }

    @Override
    public CarTransfer findCarTransferById(Long id) {
        return carTransferRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoudException(String.format("Cart transfer with id: %d, not found", id)));
    }
}
