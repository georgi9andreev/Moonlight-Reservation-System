package com.bootcamp3.MoonlightHotelAndSpa.validator;

import com.bootcamp3.MoonlightHotelAndSpa.exception.RecordNotFoudException;
import com.bootcamp3.MoonlightHotelAndSpa.repository.RoomReservationRepository;
import com.bootcamp3.MoonlightHotelAndSpa.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomReservationValidator {

    private final RoomReservationRepository roomReservationRepository;
    private final RoomService roomService;

    @Autowired
    public RoomReservationValidator(RoomReservationRepository roomReservationRepository, RoomService roomService) {
        this.roomReservationRepository = roomReservationRepository;
        this.roomService = roomService;
    }

    public void validateRoomReservationByIdAndRoomId(Long rid, Long id) {

        roomService.findRoomById(rid);
        validateRoomReservationById(id);
    }

    public void validateRoomReservationById(Long id) {

        roomReservationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoudException(String.format("Reservation with id: %d, not found", id)));
    }
}
