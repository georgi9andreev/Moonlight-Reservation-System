package com.bootcamp3.MoonlightHotelAndSpa.exception;

public class InvalidPhoneNumber extends RuntimeException{
    public InvalidPhoneNumber(String message) {
        super(message);
    }
}
