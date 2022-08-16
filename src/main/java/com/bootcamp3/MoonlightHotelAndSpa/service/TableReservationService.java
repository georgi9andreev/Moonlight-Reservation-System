package com.bootcamp3.MoonlightHotelAndSpa.service;

import com.bootcamp3.MoonlightHotelAndSpa.enumeration.table.TableZone;
import com.bootcamp3.MoonlightHotelAndSpa.model.table.Table;
import com.bootcamp3.MoonlightHotelAndSpa.model.table.TableReservation;

import java.util.List;

public interface TableReservationService {

    void save(TableReservation tableReservation);

    List<Table> getAllAvailableTables(int people, TableZone zone, String date, String hour);

    TableReservation getReservationByIdAndTableId(Long id, Long rid);
}
