package com.bootcamp3.MoonlightHotelAndSpa.validator;

import com.bootcamp3.MoonlightHotelAndSpa.enumeration.RoomType;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ReservationParametersValidator {

    public static void validateDates(Instant startDate, Instant endDate) {

        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("Start date can not be after end date");
        }

        if (startDate.isBefore(Instant.now())) {
            throw new RuntimeException("Invalid date. Reservation can not be in the past.");
        }
    }

    public static void validateNumberOfGuests(int adults, int kids) {

        final int MAXIMUM_ROOM_CAPACITY = 4;

        int guests = adults + kids;

        if (guests > MAXIMUM_ROOM_CAPACITY) {
            throw new RuntimeException("The number of guests entered is greater than the capacity of a room");
        }
    }

    public static void validatePeopleByRoomType(int adults, int kids, RoomType roomType) {

        int people = adults + kids;

        if (people > checkPeopleByRoomType(roomType)) {
            throw new RuntimeException("The entered number of people does not correspond to the selected room type");
        }
    }

    private static int checkPeopleByRoomType(RoomType roomType) {

        return switch (roomType) {
            case STANDARD -> 2;
            case STUDIO -> 3;
            case APARTMENT -> 4;
        };
    }
}
