package com.bootcamp3.MoonlightHotelAndSpa.service.impl;

import com.bootcamp3.MoonlightHotelAndSpa.dto.CreateOrder;
import com.bootcamp3.MoonlightHotelAndSpa.enumeration.CurrencyCode;
import com.bootcamp3.MoonlightHotelAndSpa.enumeration.ReservationPaymentStatus;
import com.bootcamp3.MoonlightHotelAndSpa.model.RoomReservation;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import com.bootcamp3.MoonlightHotelAndSpa.service.EmailService;
import com.bootcamp3.MoonlightHotelAndSpa.service.PaymentService;
import com.bootcamp3.MoonlightHotelAndSpa.service.RoomReservationService;
import com.bootcamp3.MoonlightHotelAndSpa.validator.RoomReservationValidator;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.*;

import static com.bootcamp3.MoonlightHotelAndSpa.constant.EmailConstant.EMAIL_SUBJECT_PAYMENT;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final String APPROVE_LINK_REL = "approve";

    private final PayPalHttpClient payPalHttpClient;
    private final RoomReservationValidator roomReservationValidator;
    private final RoomReservationService roomReservationService;
    private final EmailService emailService;

    @Autowired
    public PaymentServiceImpl(@Value("${paypal.client.id}") String clientId,
                              @Value("${paypal.client.secret}") String clientSecret, RoomReservationValidator roomReservationValidator, RoomReservationService roomReservationService, EmailService emailService) {
        this.roomReservationValidator = roomReservationValidator;
        this.roomReservationService = roomReservationService;
        this.emailService = emailService;
        payPalHttpClient = new PayPalHttpClient(new PayPalEnvironment.Sandbox(clientId, clientSecret));
    }

    @Override
    @SneakyThrows
    public CreateOrder createOrder(Long rid, URI returnUrl) {

        roomReservationValidator.validateRoomReservationById(rid);
        RoomReservation foundRoomReservation = roomReservationService.findById(rid);
        Double totalAmount = foundRoomReservation.getTotalPrice();

        final OrdersCreateRequest ordersCreateRequest = new OrdersCreateRequest().requestBody(buildRequestBody(totalAmount, returnUrl, foundRoomReservation));
        final HttpResponse<Order> orderHttpResponse = payPalHttpClient.execute(ordersCreateRequest);
        final Order order = orderHttpResponse.result();
        LinkDescription approveUri = extractApprovalLink(order);
        return new CreateOrder(order.id(),URI.create(approveUri.href()));
    }

    private OrderRequest buildRequestBody(Double totalAmount, URI returnUrl, RoomReservation roomReservation) {
        OrderRequest orderRequest = new OrderRequest();

        String currency = CurrencyCode.EUR.getValue;
        String handlingTax = "10";
        String totalTax = "20";
        double totalCost = Double.parseDouble(handlingTax) + Double.parseDouble(totalTax);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().referenceId("PUHF")
                .description("Hotel room reservation")
                .customId("CUST-MoonlightHotelAndSpa").softDescriptor("MoonlightHotelAndSpa")
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode(currency).value(totalAmount.toString())
                        .amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode(currency).value(String.valueOf(totalAmount - totalCost)))
                                .handling(new Money().currencyCode(currency).value(handlingTax))
                                .taxTotal(new Money().currencyCode(currency).value(totalTax))))
                .items(new ArrayList<Item>() {
                    {
                        add(new Item().name("Room").description(roomReservation.getRoom().getDescription()).sku("sku01")
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
    public void captureOrder(String orderId, Long rid) {
        final OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest(orderId);
        final HttpResponse<Order> httpResponse = payPalHttpClient.execute(ordersCaptureRequest);

        changeRoomReservationPaymentStatus(rid);
    }

    private void sendPaymentInformationToUserEmail(User user, RoomReservation roomReservation) {

        Map<String, Object> model = new HashMap<>();
        model.put("TotalPrice", roomReservation.getTotalPrice());
        model.put("RoomType", roomReservation.getRoom().getTitle().toString());
        model.put("Days", "2");
        model.put("View", roomReservation.getRoom().getRoomView().toString());
        model.put("BedType", "Double");
        model.put("Adults", roomReservation.getAdults());
        model.put("Kids", roomReservation.getKids());
        model.put("StartDate", roomReservation.getCheckIn());
        model.put("EndDate", roomReservation.getCheckOut());
        model.put("Name", user.getFirstName() + " " + user.getLastName());
        model.put("Address", "Some address");
        model.put("Phone", user.getPhoneNumber());
        model.put("email", user.getEmail());

        emailService.sendHtmlEmail(user.getEmail(), EMAIL_SUBJECT_PAYMENT, model);
    }

    private void changeRoomReservationPaymentStatus(Long rid) {
        RoomReservation foundRoomReservation = roomReservationService.findById(rid);
        foundRoomReservation.setStatus(ReservationPaymentStatus.PAID);

        sendPaymentInformationToUserEmail(foundRoomReservation.getUser(), foundRoomReservation);
    }

    private OrderRequest setApplicationContext(URI returnUrl, OrderRequest orderRequest) {
        return orderRequest.applicationContext(new ApplicationContext().returnUrl(returnUrl.toString()));
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
}