package com.bootcamp3.MoonlightHotelAndSpa.exception;

public class RoomNotFoundException extends RuntimeException{

    public RoomNotFoundException(String message) {
        super(message);
    }
}
