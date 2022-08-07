package com.bootcamp3.MoonlightHotelAndSpa.validator;

import com.bootcamp3.MoonlightHotelAndSpa.model.Room;
import com.bootcamp3.MoonlightHotelAndSpa.model.RoomReservation;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ReservationParametersValidator {

    public void validateDates(Instant startDate, Instant endDate) {

        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("Start date can not be after end date");
        }

        if (startDate.isBefore(Instant.now())) {
            throw new RuntimeException("Invalid date. Reservation can not be in the past.");
        }
    }

    public void validateNumberOfGuests(RoomReservation roomReservation, Room room) {

        int guests = roomReservation.getAdults() + roomReservation.getKids();

        if (guests > room.getPeople()) {
            throw new RuntimeException("Capacity of current room not enough");
        }
    }
}
