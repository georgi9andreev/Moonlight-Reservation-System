package com.bootcamp3.MoonlightHotelAndSpa.service.impl;

import com.bootcamp3.MoonlightHotelAndSpa.converter.TableConverter;
import com.bootcamp3.MoonlightHotelAndSpa.dto.restaurant.TableRequest;
import com.bootcamp3.MoonlightHotelAndSpa.exception.RecordNotFoudException;
import com.bootcamp3.MoonlightHotelAndSpa.model.table.Table;
import com.bootcamp3.MoonlightHotelAndSpa.repository.TableRepository;
import com.bootcamp3.MoonlightHotelAndSpa.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;

    @Autowired
    public TableServiceImpl(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @Override
    public void save(Table table) {

        tableRepository.save(table);
    }

    @Override
    public Table findById(Long id) {
        return tableRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoudException(String.format("Table with id: %d, not found", id)));
    }

    @Override
    public void update(Long id, TableRequest request) {

        Table table = findById(id);

        save(TableConverter.update(table, request));
    }
}
