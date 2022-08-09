package com.bootcamp3.MoonlightHotelAndSpa.validator;

import com.bootcamp3.MoonlightHotelAndSpa.exception.RecordNotFoudException;
import com.bootcamp3.MoonlightHotelAndSpa.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomValidator {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomValidator(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void validateRoomByType(String roomType) {
        roomRepository.findByTitle(roomType)
                .orElseThrow(() -> new RecordNotFoudException(String.format("Room with type: %s, not found", roomType)));
    }

    public void validateRoomById(Long rid) {

        roomRepository.findById(rid)
                .orElseThrow(() -> new RecordNotFoudException(String.format("Room with id: %d, not found", rid)));
    }
}
