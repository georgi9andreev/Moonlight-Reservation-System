package com.bootcamp3.MoonlightHotelAndSpa.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
        super(message);
    }
}
