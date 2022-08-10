package com.bootcamp3.MoonlightHotelAndSpa.controller;

import com.bootcamp3.MoonlightHotelAndSpa.converter.TableConverter;
import com.bootcamp3.MoonlightHotelAndSpa.dto.restaurant.TableRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.restaurant.TableResponse;
import com.bootcamp3.MoonlightHotelAndSpa.model.table.Table;
import com.bootcamp3.MoonlightHotelAndSpa.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tables")
public class RestaurantController {

    private final TableService tableService;

    @Autowired
    public RestaurantController(TableService tableService) {
        this.tableService = tableService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<TableResponse> createTable(@RequestBody TableRequest request) {

        Table table = TableConverter.convertToTable(request);

        tableService.save(table);

        TableResponse response = TableConverter.convertToTableResponse(table);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
