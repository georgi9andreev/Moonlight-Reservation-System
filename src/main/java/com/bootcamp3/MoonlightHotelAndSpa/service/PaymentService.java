package com.bootcamp3.MoonlightHotelAndSpa.service;

import com.bootcamp3.MoonlightHotelAndSpa.dto.CreateOrder;
import com.bootcamp3.MoonlightHotelAndSpa.dto.PaymentDto;

import javax.servlet.http.HttpServletRequest;

public interface PaymentService {

    CreateOrder createOrder(PaymentDto payment, HttpServletRequest request);

    public void captureOrder(String orderId, Long rid);
}
