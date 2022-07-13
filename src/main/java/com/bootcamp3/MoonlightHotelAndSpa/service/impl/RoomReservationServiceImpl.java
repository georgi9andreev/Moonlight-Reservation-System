package com.bootcamp3.MoonlightHotelAndSpa.service.impl;

import com.bootcamp3.MoonlightHotelAndSpa.model.RoomReservation;
import com.bootcamp3.MoonlightHotelAndSpa.repository.RoomReservationRepository;
import com.bootcamp3.MoonlightHotelAndSpa.service.RoomReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomReservationServiceImpl implements RoomReservationService {

    private final RoomReservationRepository roomReservationRepository;

    @Autowired
    public RoomReservationServiceImpl(RoomReservationRepository roomReservationRepository) {
        this.roomReservationRepository = roomReservationRepository;
    }

    @Override
    public void save(RoomReservation roomReservation) {

        roomReservationRepository.save(roomReservation);
    }
}
