package com.bootcamp3.MoonlightHotelAndSpa.service.impl;

import com.bootcamp3.MoonlightHotelAndSpa.model.table.TableReservation;
import com.bootcamp3.MoonlightHotelAndSpa.repository.TableReservationRepository;
import com.bootcamp3.MoonlightHotelAndSpa.service.TableReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
