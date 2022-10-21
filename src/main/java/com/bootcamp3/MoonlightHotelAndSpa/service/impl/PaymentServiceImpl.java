package com.bootcamp3.MoonlightHotelAndSpa.service.impl;

import com.bootcamp3.MoonlightHotelAndSpa.dto.CreateOrder;
import com.bootcamp3.MoonlightHotelAndSpa.dto.PaymentDto;
import com.bootcamp3.MoonlightHotelAndSpa.enumeration.CurrencyCode;
import com.bootcamp3.MoonlightHotelAndSpa.service.EmailService;
import com.bootcamp3.MoonlightHotelAndSpa.service.PaymentService;
import com.bootcamp3.MoonlightHotelAndSpa.service.RoomReservationService;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final String APPROVE_LINK_REL = "approve";

    private final PayPalHttpClient payPalHttpClient;
    private final EmailService emailService;
    private final RoomReservationService roomReservationService;

    @Autowired
    public PaymentServiceImpl(@Value("${paypal.client.id}") String clientId,
                       @Value("${paypal.client.secret}") String clientSecret, EmailService emailService, RoomReservationService roomReservationService) {
        this.emailService = emailService;
        this.roomReservationService = roomReservationService;
        payPalHttpClient = new PayPalHttpClient(new PayPalEnvironment.Sandbox(clientId, clientSecret));
    }

    @Override
    @SneakyThrows
    public CreateOrder createOrder(PaymentDto payment, HttpServletRequest request) {

        final URI returnUrl = buildReturnUrl(request, payment.getId(), payment);
        Double totalAmount = payment.getTotalAmount();

        final OrdersCreateRequest ordersCreateRequest = new OrdersCreateRequest().requestBody(buildRequestBody(totalAmount, returnUrl, payment));
        final HttpResponse<Order> orderHttpResponse = payPalHttpClient.execute(ordersCreateRequest);
        final Order order = orderHttpResponse.result();
        LinkDescription approveUri = extractApprovalLink(order);
        return new CreateOrder(order.id(),URI.create(approveUri.href()));
    }

    private OrderRequest buildRequestBody(Double totalAmount, URI returnUrl, PaymentDto payment) {
        OrderRequest orderRequest = new OrderRequest();

        String currency = CurrencyCode.EUR.getValue;
        String handlingTax = "10";
        String totalTax = "20";
        double totalCost = Double.parseDouble(handlingTax) + Double.parseDouble(totalTax);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().referenceId("PUHF")
                .description(payment.getDescription())
                .customId("CUST-MoonlightHotelAndSpa").softDescriptor("MoonlightHotelAndSpa")
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode(currency).value(totalAmount.toString())
                        .amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode(currency).value(String.valueOf(totalAmount - totalCost)))
                                .handling(new Money().currencyCode(currency).value(handlingTax))
                                .taxTotal(new Money().currencyCode(currency).value(totalTax))))
                .items(new ArrayList<Item>() {
                    {
                        add(new Item().name(payment.getItemDescription()).description(payment.getItemDescription()).sku("sku01")
                                .unitAmount(new Money().currencyCode(currency).value(String.valueOf(totalAmount - totalCost)))
                                .tax(new Money().currencyCode(currency).value(totalTax)).quantity("1"));
                    }
                })
                .shippingDetail(new ShippingDetail().name(new Name().fullName("Advance Academy"))
                        .addressPortable(new AddressPortable().addressLine1("19, Dimitar Ikonomov str.").addressLine2("Floor 3")
                                .adminArea2("Varna").adminArea1("VN").postalCode("9000").countryCode("BG")));
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);

        setApplicationContext(returnUrl, orderRequest);
        setCheckoutIntent(orderRequest);
        return orderRequest;
    }

    @Override
    @SneakyThrows
    @Transactional
    public void captureOrder(String orderId, Long id) {
        final OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest(orderId);
        final HttpResponse<Order> httpResponse = payPalHttpClient.execute(ordersCaptureRequest);
    }

    private void setApplicationContext(URI returnUrl, OrderRequest orderRequest) {
        orderRequest.applicationContext(new ApplicationContext().returnUrl(returnUrl.toString()));
    }

    private void setCheckoutIntent(OrderRequest orderRequest) {
        orderRequest.checkoutPaymentIntent("CAPTURE");
    }

    private LinkDescription extractApprovalLink(Order order) {
        return order.links().stream()
                .filter(link -> APPROVE_LINK_REL.equals(link.rel()))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    private URI buildReturnUrl(HttpServletRequest request, Long id, PaymentDto payment) {
        String idAsString = payment.getClassTypeId() + id;

        try {
            URI requestUri = URI.create(request.getRequestURL().toString());
            return new URI(requestUri.getScheme(),
                    requestUri.getUserInfo(),
                    requestUri.getHost(),
                    requestUri.getPort(),
                    payment.getCapturePath(),
                    idAsString,
                    null);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
