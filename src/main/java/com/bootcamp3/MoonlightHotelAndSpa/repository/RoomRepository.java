package com.bootcamp3.MoonlightHotelAndSpa.repository;

import com.bootcamp3.MoonlightHotelAndSpa.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
