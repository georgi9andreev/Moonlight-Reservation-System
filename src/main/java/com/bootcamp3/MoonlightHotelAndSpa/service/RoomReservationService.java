package com.bootcamp3.MoonlightHotelAndSpa.service;

import com.bootcamp3.MoonlightHotelAndSpa.model.RoomReservation;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;

import java.util.List;

public interface RoomReservationService {

    void save(RoomReservation roomReservation);

    List<RoomReservation> getByUser(User user);

    List<RoomReservation> getAll();
}
