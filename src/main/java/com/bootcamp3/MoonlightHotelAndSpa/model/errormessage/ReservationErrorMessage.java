package com.bootcamp3.MoonlightHotelAndSpa.model.errormessage;

import java.util.Set;

public class ReservationErrorMessage {

    private String message;
    private Set<String> errors;

    public ReservationErrorMessage() {
    }

    public ReservationErrorMessage(String message, Set<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<String> getErrors() {
        return errors;
    }

    public void setErrors(Set<String> errors) {
        this.errors = errors;
    }
}
