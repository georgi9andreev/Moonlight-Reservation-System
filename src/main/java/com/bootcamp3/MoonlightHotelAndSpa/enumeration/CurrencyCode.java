package com.bootcamp3.MoonlightHotelAndSpa.enumeration;

public enum CurrencyCode {

    EUR("EUR"),
    USD("USD"),
    BGN("BGN");

    public final String getValue;

    private CurrencyCode(String value) {
        this.getValue = value;
    }
}
