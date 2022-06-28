package com.bootcamp3.MoonlightHotelAndSpa.model;

public class ErrorResponse {

    private String message;
    private ErrorField errors;

    public ErrorResponse() {
    }

    public ErrorResponse(String message, ErrorField errors) {
        this.message = message;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorField getErrors() {
        return errors;
    }

    public void setErrors(ErrorField errors) {
        this.errors = errors;
    }
}
