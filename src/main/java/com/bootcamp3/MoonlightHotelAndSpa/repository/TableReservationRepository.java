package com.bootcamp3.MoonlightHotelAndSpa.repository;

import com.bootcamp3.MoonlightHotelAndSpa.enumeration.table.TableZone;
import com.bootcamp3.MoonlightHotelAndSpa.model.table.Table;
import com.bootcamp3.MoonlightHotelAndSpa.model.table.TableReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TableReservationRepository extends JpaRepository<TableReservation, Long> {

    @Query("select t from Table t where t.people >= :people and t.zone = :zone and t.id not in " +
            "(select tr.table from TableReservation tr) or t.id in (select ttr.table from TableReservation ttr where " +
            "ttr.date not between :start and :end)")
    List<Table> getAllAvailableTables(@Param("people") int people,
                                      @Param("zone") TableZone tableZone,
                                      @Param("start") Instant start,
                                      @Param("end") Instant end);
}

