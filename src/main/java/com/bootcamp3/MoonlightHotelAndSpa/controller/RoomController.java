package com.bootcamp3.MoonlightHotelAndSpa.controller;

import com.bootcamp3.MoonlightHotelAndSpa.converter.RoomConverter;
import com.bootcamp3.MoonlightHotelAndSpa.converter.RoomReservationConverter;
import com.bootcamp3.MoonlightHotelAndSpa.dto.RoomReservation.RoomReservationRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.RoomReservation.RoomReservationResponse;
import com.bootcamp3.MoonlightHotelAndSpa.dto.room.RoomRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.room.RoomResponse;
import com.bootcamp3.MoonlightHotelAndSpa.exception.RoomNotFoundException;
import com.bootcamp3.MoonlightHotelAndSpa.model.Room;
import com.bootcamp3.MoonlightHotelAndSpa.model.RoomReservation;
import com.bootcamp3.MoonlightHotelAndSpa.service.RoomReservationService;
import com.bootcamp3.MoonlightHotelAndSpa.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.bootcamp3.MoonlightHotelAndSpa.constant.ExceptionConstant.ROOM_NOT_FOUND;

@RestController
@RequestMapping(value = "/rooms")
public class RoomController {

    private final RoomService roomService;
    private final RoomReservationService roomReservationService;

    @Autowired
    public RoomController(RoomService roomService, RoomReservationService roomReservationService) {
        this.roomService = roomService;
        this.roomReservationService = roomReservationService;
    }

    @PostMapping(value = "/{id}/reservations")
    public ResponseEntity<RoomReservationResponse> createRoomReservation(@PathVariable Long id, @RequestBody RoomReservationRequest request) {

        RoomReservation roomReservation = RoomReservationConverter.convertToRoomReservation(id, request);

        roomReservationService.save(roomReservation);

        RoomReservationResponse roomReservationResponse = RoomReservationConverter.convertToRoomReservationResponse(id, roomReservation, request);

        return new ResponseEntity<>(roomReservationResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(@RequestBody RoomRequest request) {

        Room room = RoomConverter.convertToRoom(request);

        roomService.save(room);

        RoomResponse roomResponse = RoomConverter.convertToRoomResponse(room);

        return new ResponseEntity<>(roomResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<RoomResponse> findById(@PathVariable Long id) {

        RoomResponse room = RoomConverter.convertToRoomResponse(roomService.findRoomById(id));

        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Set<RoomResponse>> findAll() {

         return new ResponseEntity<>(roomService.getAllRooms(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long id, @RequestBody RoomRequest request) {

        Room room = roomService.updateRoom(id, request);

        RoomResponse response = RoomConverter.convertToRoomResponse(room);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {

        try {
            roomService.deleteById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {

            throw new RoomNotFoundException(String.format(ROOM_NOT_FOUND, id));
        }
    }
}
