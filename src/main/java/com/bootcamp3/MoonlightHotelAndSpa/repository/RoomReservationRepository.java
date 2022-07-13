package com.bootcamp3.MoonlightHotelAndSpa.repository;

import com.bootcamp3.MoonlightHotelAndSpa.model.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {
}
