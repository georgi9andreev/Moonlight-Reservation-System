package com.bootcamp3.MoonlightHotelAndSpa.service.impl;

import com.bootcamp3.MoonlightHotelAndSpa.converter.TableReservationConverter;
import com.bootcamp3.MoonlightHotelAndSpa.enumeration.table.TableZone;
import com.bootcamp3.MoonlightHotelAndSpa.exception.RecordNotFoudException;
import com.bootcamp3.MoonlightHotelAndSpa.model.table.Table;
import com.bootcamp3.MoonlightHotelAndSpa.model.table.TableReservation;
import com.bootcamp3.MoonlightHotelAndSpa.repository.TableReservationRepository;
import com.bootcamp3.MoonlightHotelAndSpa.service.TableReservationService;
import com.bootcamp3.MoonlightHotelAndSpa.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TableReservationServiceImpl implements TableReservationService {

    private final TableReservationRepository tableReservationRepository;
    private final TableService tableService;

    @Autowired
    public TableReservationServiceImpl(TableReservationRepository tableReservationRepository, TableService tableService) {
        this.tableReservationRepository = tableReservationRepository;
        this.tableService = tableService;
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

    @Override
    public TableReservation getReservationByIdAndTableId(Long id, Long rid) {

        Table table = tableService.findById(id);
        TableReservation tableReservation = tableReservationRepository.findById(rid)
                .orElseThrow(() -> new RecordNotFoudException(String.format("Table reservation with id: %s, not found", rid)));

        if (!id.equals(tableReservation.getTable().getId())) {
            throw new RuntimeException("Table id does not match");
        }

        return tableReservation;
    }

    @Override
    public List<TableReservation> getAllReservationsByTable(Long id) {

        Table foundTable = tableService.findById(id);
        return tableReservationRepository.findByTable(foundTable);
    }
}
