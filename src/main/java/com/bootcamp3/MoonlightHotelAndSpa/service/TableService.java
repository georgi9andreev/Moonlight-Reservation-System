package com.bootcamp3.MoonlightHotelAndSpa.service;

import com.bootcamp3.MoonlightHotelAndSpa.dto.restaurant.TableRequest;
import com.bootcamp3.MoonlightHotelAndSpa.model.table.Table;

public interface TableService {

    void save(Table table);

    Table findById(Long id);

    void update(Long id, TableRequest request);

//    List<Table> findAllTables();
}
