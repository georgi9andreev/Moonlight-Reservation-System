package com.bootcamp3.MoonlightHotelAndSpa.service;

import com.bootcamp3.MoonlightHotelAndSpa.dto.transfer.CarTransferRequest;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import com.bootcamp3.MoonlightHotelAndSpa.model.car.CarTransfer;

import java.util.List;

public interface CarTransferService {

    CarTransfer createCarTransfer(Long id, CarTransferRequest carTransferRequest, User user);

    List<CarTransfer> getAllTransfersByCarId(Long id);
}
