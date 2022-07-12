package com.bootcamp3.MoonlightHotelAndSpa.service;

import com.bootcamp3.MoonlightHotelAndSpa.dto.room.RoomRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.room.RoomResponse;
import com.bootcamp3.MoonlightHotelAndSpa.model.Room;

import java.util.Set;

public interface RoomService {

    Room findRoomById(Long id);

    void save(Room room);

    Set<RoomResponse> getAllRooms();

    Room updateRoom(Long id, RoomRequest roomRequest);

    void deleteById(Long id);
}
