package com.bootcamp3.MoonlightHotelAndSpa.converter;

import com.bootcamp3.MoonlightHotelAndSpa.dto.UserResponse;
import com.bootcamp3.MoonlightHotelAndSpa.dto.restaurant.TableReservationRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.restaurant.TableReservationResponse;
import com.bootcamp3.MoonlightHotelAndSpa.dto.restaurant.TableResponse;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import com.bootcamp3.MoonlightHotelAndSpa.model.table.Table;
import com.bootcamp3.MoonlightHotelAndSpa.model.table.TableReservation;
import com.bootcamp3.MoonlightHotelAndSpa.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class TableReservationConverter {

    private static TableService tableService;

    @Autowired
    public TableReservationConverter(TableService tableService) {

        TableReservationConverter.tableService = tableService;
    }

    public static TableReservation convertToTableReservation(Long id, TableReservationRequest request, User user) {

        Table table = tableService.findById(id);

        TableReservation tableReservation = new TableReservation();
        tableReservation.setDate(convertRequestDateAndHourToInstant(request.getDate(), request.getHour()));
        tableReservation.setPeople(request.getPeople());
        tableReservation.setPrice(request.getPrice());
        tableReservation.setUpdated("Reserved");
        tableReservation.setTable(table);
        tableReservation.setUser(user);

        return tableReservation;
    }

    public static TableReservationResponse convertToTableReservationResponse(TableReservation tableReservation) {

        TableResponse tableResponse = TableConverter.convertToTableResponse(tableReservation.getTable());
        UserResponse userResponse = UserConverter.convertToUserDto(tableReservation.getUser());

        return new TableReservationResponse.Builder()
                .addId(tableReservation.getId())
                .addDate(tableReservation.getDate())
                .addPeople(tableReservation.getPeople())
                .addPrice(tableReservation.getPrice())
                .addUpdated(tableReservation.getUpdated())
                .addTable(tableResponse)
                .addUser(userResponse)
                .build();
    }

    public static Instant convertRequestDateAndHourToInstant(String date, String hour) {

        String concatenatedDate = date + " " + hour;

        return LocalDateTime
                .parse(concatenatedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .atZone(ZoneId.systemDefault())
                .toInstant();
    }
}
