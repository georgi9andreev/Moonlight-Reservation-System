package com.bootcamp3.MoonlightHotelAndSpa.controller;

import com.bootcamp3.MoonlightHotelAndSpa.annotation.openapidocs.transfer.*;
import com.bootcamp3.MoonlightHotelAndSpa.converter.CarCategoryConverter;
import com.bootcamp3.MoonlightHotelAndSpa.converter.CarConverter;
import com.bootcamp3.MoonlightHotelAndSpa.converter.CarTransferConverter;
import com.bootcamp3.MoonlightHotelAndSpa.dto.transfer.*;
import com.bootcamp3.MoonlightHotelAndSpa.exception.RecordNotFoudException;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import com.bootcamp3.MoonlightHotelAndSpa.model.car.Car;
import com.bootcamp3.MoonlightHotelAndSpa.model.car.CarCategory;
import com.bootcamp3.MoonlightHotelAndSpa.model.car.CarTransfer;
import com.bootcamp3.MoonlightHotelAndSpa.service.CarCategoryService;
import com.bootcamp3.MoonlightHotelAndSpa.service.CarService;
import com.bootcamp3.MoonlightHotelAndSpa.service.CarTransferService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "cars", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Transfers", description = "Actions with Transfers")
public class TransferController {

    private final CarCategoryService carCategoryService;
    private final CarService carService;
    private final CarTransferService carTransferService;

    @Autowired
    public TransferController(CarCategoryService carCategoryService, CarService carService, CarTransferService carTransferService) {
        this.carCategoryService = carCategoryService;
        this.carService = carService;
        this.carTransferService = carTransferService;
    }

    @PostMapping(value = "categories")
    @CreateCarCategoryApiDocs
    public ResponseEntity<CarCategoryResponse> createCarCategory(@RequestBody CarCategoryRequest request) {

        CarCategory carCategory = CarCategoryConverter.convertToCarCategory(request);
        carCategoryService.createCarCategory(carCategory);

        CarCategoryResponse response = CarCategoryConverter.convertToCarCategoryResponse(carCategory);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping
    @CreateNewCarApiDocs
    public ResponseEntity<CarResponse> createCar(@RequestBody CarRequest request) {

        Car car = CarConverter.convertToCar(request);
        carService.createCar(car);

        CarResponse carResponse = CarConverter.convertToCarResponse(car);
        return new ResponseEntity<>(carResponse, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    @DeleteCarApiDocs
    public ResponseEntity<HttpStatus> deleteCar(@PathVariable Long id) {

        try {
            carService.deleteCarById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {

            throw new RecordNotFoudException(String.format("Car with id: %d, not found", id));
        }
    }

    @DeleteMapping(value = "/categories/{id}")
    @DeleteCarCategoryApiDocs
    public ResponseEntity<HttpStatus> deleteCarCategory(@PathVariable Long id) {

        try {
            carCategoryService.deleteCarCategoryById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {

            throw new RecordNotFoudException(String.format("Car category with id: %d, not found", id));
        }
    }

    @PutMapping(value = "/{id}")
    @UpdateCarApiDocs
    public ResponseEntity<CarResponse> updateCar(@PathVariable Long id, @RequestBody CarRequest carRequest) {

        Car updatedCar = carService.updateCarById(id, carRequest);

        CarResponse carResponse = CarConverter.convertToCarResponse(updatedCar);

        return new ResponseEntity<>(carResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/categories/{id}")
    @UpdateCarCategoryApiDocs
    public ResponseEntity<CarCategoryResponse> updateCarCategory(@PathVariable Long id, @RequestBody CarCategoryRequest carCategoryRequest) {

        CarCategory updatedCarCategory = carCategoryService.updateCarCategory(id, carCategoryRequest);

        CarCategoryResponse carCategoryResponse = CarCategoryConverter.convertToCarCategoryResponse(updatedCarCategory);

        return new ResponseEntity<>(carCategoryResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/transfers")
    @CreateCarTransferApiDocs
    public ResponseEntity<CarTransferResponse> createCarTransfer(@PathVariable Long id, @RequestBody CarTransferRequest carTransferRequest,
                                                                 @AuthenticationPrincipal User user) {

        CarTransfer carTransfer = carTransferService.createCarTransfer(id, carTransferRequest, user);
        CarTransferResponse carTransferResponse = CarTransferConverter.convertToCarTransferResponse(carTransfer);

        return new ResponseEntity<>(carTransferResponse, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}/transfers")
    @GetCarTransfersApiDocs
    public ResponseEntity<List<CarTransferResponse>> getAllTransfersByCarId(@PathVariable Long id) {

        List<CarTransfer> carTransfers = carTransferService.getAllTransfersByCarId(id);

        List<CarTransferResponse> carTransferResponses = carTransfers
                .stream()
                .map(CarTransferConverter::convertToCarTransferResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(carTransferResponses, HttpStatus.OK);
    }

    @GetMapping(value = "/available")
    @GetAvailableCars
    public ResponseEntity<List<CarResponse>> getAvailableCars(@RequestParam Instant date,
                                                              @RequestParam int seats) {

        List<Car> getAvailableCarsBySeatsAndCarTransferDate = carService.getAvailableCars(seats, date);

        List<CarResponse> carResponses = getAvailableCarsBySeatsAndCarTransferDate
                .stream()
                .map(CarConverter::convertToCarResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(carResponses, HttpStatus.OK);
    }
}
