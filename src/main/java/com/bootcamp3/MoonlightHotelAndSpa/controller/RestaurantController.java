package com.bootcamp3.MoonlightHotelAndSpa.controller;

import com.bootcamp3.MoonlightHotelAndSpa.converter.TableConverter;
import com.bootcamp3.MoonlightHotelAndSpa.converter.TableReservationConverter;
import com.bootcamp3.MoonlightHotelAndSpa.dto.CreateOrder;
import com.bootcamp3.MoonlightHotelAndSpa.dto.PaymentDto;
import com.bootcamp3.MoonlightHotelAndSpa.dto.restaurant.*;
import com.bootcamp3.MoonlightHotelAndSpa.enumeration.ClassType;
import com.bootcamp3.MoonlightHotelAndSpa.enumeration.table.TableZone;
import com.bootcamp3.MoonlightHotelAndSpa.exception.RecordNotFoudException;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import com.bootcamp3.MoonlightHotelAndSpa.model.table.Table;
import com.bootcamp3.MoonlightHotelAndSpa.model.table.TableReservation;
import com.bootcamp3.MoonlightHotelAndSpa.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tables", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Restaurant", description = "Actions with Restaurant tables")
@CrossOrigin()
public class RestaurantController {

    private final TableService tableService;
    private final TableReservationService tableReservationService;
    private final UserService userService;
    private final PaymentService paymentService;

    @Autowired
    public RestaurantController(TableService tableService, TableReservationService tableReservationService, UserService userService, PaymentService paymentService) {
        this.tableService = tableService;
        this.tableReservationService = tableReservationService;
        this.userService = userService;
        this.paymentService = paymentService;
    }

    @PutMapping(value = "/{id}/reservations/{rid}")
    public ResponseEntity<TableReservationResponse> updateTableReservations(@PathVariable Long id, @PathVariable Long rid, @RequestBody TableReservationUpdateRequest request) {

        tableReservationService.updateTableReservation(id, rid, request);

        TableReservationResponse response = TableReservationConverter.convertToTableReservationResponse(tableReservationService.findTableReservationById(rid));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/reservations")
    public ResponseEntity<List<TableReservationResponse>> getAllReservationsByTable(@PathVariable Long id) {

        List<TableReservation> tableReservations = tableReservationService.getAllReservationsByTable(id);

        List<TableReservationResponse> response = tableReservations.stream().map(TableReservationConverter::convertToTableReservationResponse).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/reservations/{rid}")
    public ResponseEntity<TableReservationResponse> getTableReservationByIdAndTableId(@PathVariable Long id, @PathVariable Long rid) {

        TableReservation tableReservation = tableReservationService.getReservationByIdAndTableId(id, rid);
        TableReservationResponse response = TableReservationConverter.convertToTableReservationResponse(tableReservation);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TableResponse>> getAllAvailableTable(@RequestParam int people,
                                                                    @RequestParam TableZone zone,
                                                                    @RequestParam String date,
                                                                    @RequestParam String hour) {

        List<Table> tables = tableReservationService.getAllAvailableTables(people, zone, date, hour);

        List<TableResponse> tableResponses = tables.stream().map(TableConverter::convertToTableResponse).collect(Collectors.toList());

        return new ResponseEntity<>(tableResponses, HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteTableById(@PathVariable Long id) {

        try {
            tableService.deleteTable(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {

            throw new RecordNotFoudException(String.format("Table with id: %s, not found", id));
        }
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<TableResponse> createTable(@RequestBody TableRequest request) {

        Table table = TableConverter.convertToTable(request);

        tableService.save(table);

        TableResponse response = TableConverter.convertToTableResponse(table);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyRole('ROLE_CLIENT')")
    @PostMapping(value = "/{id}/reservations")
    public ResponseEntity<TableReservationResponse> createTableReservation(@PathVariable Long id, @RequestBody TableReservationRequest request, @AuthenticationPrincipal User user) {

        //TO DO
        // 1.Validator to check is Table not reserved

        TableReservation tableReservation = TableReservationConverter.convertToTableReservation(id, request, user);
        tableReservationService.save(tableReservation);

        TableReservationResponse response = TableReservationConverter.convertToTableReservationResponse(tableReservation);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<TableResponse> updateTable(@PathVariable Long id, @RequestBody TableRequest request) {

        tableService.update(id, request);

        TableResponse response = TableConverter.convertToTableResponse(tableService.findById(id));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/reservations/{rid}")
    public ResponseEntity<HttpStatus> deleteTableReservation(@PathVariable Long id, @PathVariable Long rid) {

        try {
            tableReservationService.deleteTableReservation(id, rid);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {

            throw new RecordNotFoudException(String.format("Table reservation with id: %d, not found", rid));
        }
    }

    @PostMapping(value = "/{id}/summarize")
    public ResponseEntity<TableReservationResponse> summarizeTableReservation(@PathVariable Long id,
                                                                              @RequestBody TableReservationRequest request,
                                                                              @AuthenticationPrincipal User user) {

        TableReservation tableReservation = tableReservationService.summarizeTableReservation(id, request, user);
        TableReservationResponse tableReservationResponse = TableReservationConverter.convertToTableReservationResponse(tableReservation);

        return new ResponseEntity<>(tableReservationResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/capture")
    public String captureOrder(@RequestParam String token, @RequestParam String tableReservationId){
        long tableReservationIdAsLong = Long.parseLong(tableReservationId);

        paymentService.captureOrder(token, tableReservationIdAsLong);
        tableReservationService.changeTableReservationPaymentStatus(tableReservationIdAsLong);

        return "redirect: orders";
    }

    @PostMapping(value = "/pay")
    public String placeOrder(@RequestParam Long id, HttpServletRequest request){

        TableReservation foundTableReservation = tableReservationService.findTableReservationById(id);

        PaymentDto payment = PaymentDto.builder()
                .id(id)
                .classTypeId(ClassType.TABLE_RESERVATION.getValue)
                .description("Table reservation")
                .itemDescription("Table")
                .totalAmount(foundTableReservation.getPrice())
                .capturePath("/tables/capture")
                .build();

        CreateOrder createOrder = paymentService.createOrder(payment, request);

        return createOrder.getApprovalLink().toString();
    }
}
