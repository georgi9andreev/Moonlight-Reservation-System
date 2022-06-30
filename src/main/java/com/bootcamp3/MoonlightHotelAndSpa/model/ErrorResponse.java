package com.bootcamp3.MoonlightHotelAndSpa.model;

public class ErrorResponse extends ErrorMessage{

    private ErrorField errors;

    public ErrorResponse() {
    }

    public ErrorResponse(String message, ErrorField errors) {
        super(message);
        this.errors = errors;
    }

    public ErrorField getErrors() {
        return errors;
    }

    public void setErrors(ErrorField errors) {
        this.errors = errors;
    }
}
