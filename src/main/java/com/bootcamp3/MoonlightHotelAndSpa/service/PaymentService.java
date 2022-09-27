package com.bootcamp3.MoonlightHotelAndSpa.service;

import com.bootcamp3.MoonlightHotelAndSpa.dto.CreateOrder;

import java.net.URI;

public interface PaymentService {

    CreateOrder createOrder(Long rid, URI returnUrl);

    public void captureOrder(String orderId);
}
