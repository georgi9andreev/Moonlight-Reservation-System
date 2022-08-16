package com.bootcamp3.MoonlightHotelAndSpa.service.impl;

import com.bootcamp3.MoonlightHotelAndSpa.converter.TableReservationConverter;
import com.bootcamp3.MoonlightHotelAndSpa.enumeration.table.TableZone;
import com.bootcamp3.MoonlightHotelAndSpa.model.table.Table;
import com.bootcamp3.MoonlightHotelAndSpa.model.table.TableReservation;
import com.bootcamp3.MoonlightHotelAndSpa.repository.TableReservationRepository;
import com.bootcamp3.MoonlightHotelAndSpa.service.TableReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TableReservationServiceImpl implements TableReservationService {

    private final TableReservationRepository tableReservationRepository;

    @Autowired
    public TableReservationServiceImpl(TableReservationRepository tableReservationRepository) {
        this.tableReservationRepository = tableReservationRepository;
    }

    @Override
    public void save(TableReservation tableReservation) {

        tableReservationRepository.save(tableReservation);
    }

    @Override
    public List<Table> getAllAvailableTables(int people, TableZone zone, String date, String hour) {

        Instant dateToReserve = TableReservationConverter.convertRequestDateAndHourToInstant(date, hour);

        Instant start = dateToReserve.minusSeconds(3600);
        Instant end = dateToReserve.plusSeconds(3600);

        return tableReservationRepository.getAllAvailableTables(people, zone, start, end);
    }
}
