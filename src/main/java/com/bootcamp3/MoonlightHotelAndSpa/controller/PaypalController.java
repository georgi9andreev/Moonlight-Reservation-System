package com.bootcamp3.MoonlightHotelAndSpa.controller;

import com.bootcamp3.MoonlightHotelAndSpa.dto.CreateOrder;
import com.bootcamp3.MoonlightHotelAndSpa.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@Tag(name = "Payment", description = "Payment actions")
public class PaypalController {

    private final PaymentService paymentService;


    @Autowired
    public PaypalController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/capture")
    public String captureOrder(@RequestParam String token, @RequestParam String roomReservationId){
        long roomReservationIdString = Long.parseLong(roomReservationId);

        paymentService.captureOrder(token, roomReservationIdString);

        return "redirect: orders";
    }

    @PostMapping("/pay")
    public String placeOrder(@RequestParam Long id, HttpServletRequest request){
        final URI returnUrl = buildReturnUrl(request, id);
        CreateOrder createOrder = paymentService.createOrder(id, returnUrl);
        return "redirect:"+createOrder.getApprovalLink();
    }

    private URI buildReturnUrl(HttpServletRequest request, Long id) {
        String roomReservationIdAsString = "roomReservationId=" + String.valueOf(id);

        try {
            URI requestUri = URI.create(request.getRequestURL().toString());
            return new URI(requestUri.getScheme(),
                    requestUri.getUserInfo(),
                    requestUri.getHost(),
                    requestUri.getPort(),
                    "/capture",
                    roomReservationIdAsString,
                    null);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
