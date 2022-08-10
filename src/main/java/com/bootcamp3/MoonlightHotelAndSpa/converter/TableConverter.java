package com.bootcamp3.MoonlightHotelAndSpa.converter;

import com.bootcamp3.MoonlightHotelAndSpa.dto.restaurant.TableRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.restaurant.TableResponse;
import com.bootcamp3.MoonlightHotelAndSpa.enumeration.table.TableZone;
import com.bootcamp3.MoonlightHotelAndSpa.model.table.Table;

public class TableConverter {

    public static TableResponse convertToTableResponse(Table table) {

        return new TableResponse.Builder()
                .addId(table.getId())
                .addZone(table.getZone())
                .addNumber(table.getNumber())
                .addPeople(table.getPeople())
                .addSmoking(table.isSmoking())
                .build();
    }

    public static Table convertToTable(TableRequest tableRequest) {

        Table table = new Table();
        table.setZone(tableRequest.getZone());
        table.setNumber(tableRequest.getNumber());
        table.setPeople(tableRequest.getPeople());
        table.setSmoking(isSmoking(tableRequest.getZone()));

        return table;
    }

    private static boolean isSmoking(TableZone zone) {

        return zone == TableZone.TERRACE;
    }
}
