package com.bootcamp3.MoonlightHotelAndSpa.exception;

public class EmailNotSentException extends RuntimeException{
    public EmailNotSentException() {
    }

    public EmailNotSentException(String message) {
        super(message);
    }
}
