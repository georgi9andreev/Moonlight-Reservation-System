package com.bootcamp3.MoonlightHotelAndSpa.enumeration;

public enum ClassType {

    ROOM_RESERVATION("roomReservationId="),
    CAR_TRANSFER("carTransferId="),
    TABLE_RESERVATION("tableReservationId=");

    public final String getValue;

    private ClassType(String value) {
        this.getValue = value;
    }
}
