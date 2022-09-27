package com.bootcamp3.MoonlightHotelAndSpa.controller;

import com.bootcamp3.MoonlightHotelAndSpa.dto.CreateOrder;
import com.bootcamp3.MoonlightHotelAndSpa.service.PaymentService;
import com.bootcamp3.MoonlightHotelAndSpa.service.RoomReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class PaypalController {

    private final PaymentService paymentService;
    private final RoomReservationService roomReservationService;

    @Autowired
    public PaypalController(PaymentService paymentService, RoomReservationService roomReservationService) {
        this.paymentService = paymentService;
        this.roomReservationService = roomReservationService;
    }

    @GetMapping("/capture")
    public String captureOrder(@RequestParam String token){
        paymentService.captureOrder(token);

        return "redirect: orders";
    }

    @PostMapping("/pay")
    public String placeOrder(@RequestParam Long id, HttpServletRequest request){
        final URI returnUrl = buildReturnUrl(request);
        CreateOrder createOrder = paymentService.createOrder(id, returnUrl);
        return "redirect:"+createOrder.getApprovalLink();
    }

    private URI buildReturnUrl(HttpServletRequest request) {
        try {
            URI requestUri = URI.create(request.getRequestURL().toString());
            return new URI(requestUri.getScheme(),
                    requestUri.getUserInfo(),
                    requestUri.getHost(),
                    requestUri.getPort(),
                    "/capture",
                    null, null);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
