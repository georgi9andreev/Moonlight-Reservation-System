package com.bootcamp3.MoonlightHotelAndSpa.dto;

import lombok.Data;

import java.net.URI;

@Data
public class CreateOrder {

    private final String orderId;
    private final URI approvalLink;
}
