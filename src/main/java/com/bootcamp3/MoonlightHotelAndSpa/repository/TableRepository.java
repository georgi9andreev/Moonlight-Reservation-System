package com.bootcamp3.MoonlightHotelAndSpa.repository;

import com.bootcamp3.MoonlightHotelAndSpa.model.table.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {

    Optional<Table> findByNumber(int number);
}
