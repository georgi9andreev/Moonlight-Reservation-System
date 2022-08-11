package com.bootcamp3.MoonlightHotelAndSpa.controller;

import com.bootcamp3.MoonlightHotelAndSpa.converter.TableConverter;
import com.bootcamp3.MoonlightHotelAndSpa.converter.TableReservationConverter;
import com.bootcamp3.MoonlightHotelAndSpa.dto.restaurant.TableRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.restaurant.TableReservationRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.restaurant.TableReservationResponse;
import com.bootcamp3.MoonlightHotelAndSpa.dto.restaurant.TableResponse;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import com.bootcamp3.MoonlightHotelAndSpa.model.table.Table;
import com.bootcamp3.MoonlightHotelAndSpa.model.table.TableReservation;
import com.bootcamp3.MoonlightHotelAndSpa.service.TableReservationService;
import com.bootcamp3.MoonlightHotelAndSpa.service.TableService;
import com.bootcamp3.MoonlightHotelAndSpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tables")
public class RestaurantController {

    private final TableService tableService;
    private final TableReservationService tableReservationService;
    private final UserService userService;

    @Autowired
    public RestaurantController(TableService tableService, TableReservationService tableReservationService, UserService userService) {
        this.tableService = tableService;
        this.tableReservationService = tableReservationService;
        this.userService = userService;
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
    @GetMapping(value = "/{id}/reservations")
    public ResponseEntity<TableReservationResponse> createTableReservation(@PathVariable Long id, @RequestBody TableReservationRequest request, @AuthenticationPrincipal User user) {

        User foundUser = userService.findUserById(user.getId());

        TableReservation tableReservation = TableReservationConverter.convertToTableReservation(id, request, foundUser);
        tableReservationService.save(tableReservation);

        TableReservationResponse response = TableReservationConverter.convertToTableReservationResponse(tableReservation);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
