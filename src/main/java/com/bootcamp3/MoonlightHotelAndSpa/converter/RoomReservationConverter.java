package com.bootcamp3.MoonlightHotelAndSpa.converter;

import com.bootcamp3.MoonlightHotelAndSpa.dto.RoomReservation.RoomReservationRequest;
import com.bootcamp3.MoonlightHotelAndSpa.model.RoomReservation;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import com.bootcamp3.MoonlightHotelAndSpa.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RoomReservationConverter {

    private static UserServiceImpl userService;

    @Autowired
    public RoomReservationConverter(UserServiceImpl userService) {

        RoomReservationConverter.userService = userService;
    }

    public static RoomReservation convertToRoomReservation(RoomReservationRequest request) {

        User user = userService.findUserById(request.getUser());

        RoomReservation roomReservation = new RoomReservation();
        roomReservation.setCreatedAt(Instant.now());
        roomReservation.setCheckIn(Instant.parse(request.getStart_date()));
        roomReservation.setCheckOut(Instant.parse(request.getEnd_date()));
        roomReservation.setGuests(request.getAdults() + request.getKids());
        roomReservation.setUser(user);
        roomReservation.setFacilities(request.getType_bed());

        return roomReservation;
    }
}
