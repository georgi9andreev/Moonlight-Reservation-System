package com.bootcamp3.MoonlightHotelAndSpa.model.errormessage;

import java.util.Set;

public class ErrorField {

    private Set<String> field_name;

    public ErrorField() {
    }

    public ErrorField(Set<String> field_name) {
        this.field_name = field_name;
    }

    public Set<String> getField_name() {
        return field_name;
    }

    public void setField_name(Set<String> field_name) {
        this.field_name = field_name;
    }
}


