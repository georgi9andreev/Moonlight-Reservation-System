package com.bootcamp3.MoonlightHotelAndSpa.service.impl;

import com.bootcamp3.MoonlightHotelAndSpa.converter.RoomConverter;
import com.bootcamp3.MoonlightHotelAndSpa.dto.room.RoomRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.room.RoomResponse;
import com.bootcamp3.MoonlightHotelAndSpa.exception.RoomNotFoundException;
import com.bootcamp3.MoonlightHotelAndSpa.model.Room;
import com.bootcamp3.MoonlightHotelAndSpa.repository.RoomRepository;
import com.bootcamp3.MoonlightHotelAndSpa.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bootcamp3.MoonlightHotelAndSpa.constant.ExceptionConstant.ROOM_NOT_FOUND;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room findRoomById(Long id) {

        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(String.format(ROOM_NOT_FOUND, id)));
    }

    @Override
    public void save(Room room) {
        roomRepository.save(room);
    }

    @Override
    public Set<RoomResponse> getAllRooms() {

        Set<Room> rooms = new HashSet<>(roomRepository.findAll());

        return rooms.stream().map(RoomConverter::convertToRoomResponse).collect(Collectors.toSet());
    }

    @Override
    public Room updateRoom(Long id, RoomRequest roomRequest) {

        Room room = findRoomById(id);

        Room updatedRoom = RoomConverter.update(room, roomRequest);

        roomRepository.save(updatedRoom);

        return updatedRoom;
    }

    @Override
    public void deleteById(Long id) {

        roomRepository.deleteById(id);
    }
}
