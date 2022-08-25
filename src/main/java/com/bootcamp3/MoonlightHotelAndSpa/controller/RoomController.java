package com.bootcamp3.MoonlightHotelAndSpa.controller;

import com.bootcamp3.MoonlightHotelAndSpa.annotation.openapidocs.room.*;
import com.bootcamp3.MoonlightHotelAndSpa.converter.RoomConverter;
import com.bootcamp3.MoonlightHotelAndSpa.converter.RoomReservationConverter;
import com.bootcamp3.MoonlightHotelAndSpa.dto.RoomReservation.RoomReservationRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.RoomReservation.RoomReservationResponse;
import com.bootcamp3.MoonlightHotelAndSpa.dto.room.AvailableRoomRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.room.RoomRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.room.RoomResponse;
import com.bootcamp3.MoonlightHotelAndSpa.exception.RoomNotFoundException;
import com.bootcamp3.MoonlightHotelAndSpa.model.Room;
import com.bootcamp3.MoonlightHotelAndSpa.model.RoomReservation;
import com.bootcamp3.MoonlightHotelAndSpa.service.RoomReservationService;
import com.bootcamp3.MoonlightHotelAndSpa.service.RoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.bootcamp3.MoonlightHotelAndSpa.constant.ExceptionConstant.ROOM_NOT_FOUND;

@RestController
@RequestMapping(value = "/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Rooms", description = "Actions with Rooms")
public class RoomController {

    private final RoomService roomService;
    private final RoomReservationService roomReservationService;

    @Autowired
    public RoomController(RoomService roomService, RoomReservationService roomReservationService) {
        this.roomService = roomService;
        this.roomReservationService = roomReservationService;
    }

    //@PreAuthorize("hasAnyRole('ROLE_CLIENT')")
    @PostMapping(value = "/{id}/reservations")
    @CreateRoomReservationApiDocs
    public ResponseEntity<RoomReservationResponse> createRoomReservation(@PathVariable Long id, @RequestBody RoomReservationRequest request) {

        RoomReservation roomReservation = RoomReservationConverter.convertToRoomReservation(id, request);

        roomReservationService.save(roomReservation);

        RoomReservationResponse roomReservationResponse = RoomReservationConverter.convertToRoomReservationResponse(id, roomReservation);

        return new ResponseEntity<>(roomReservationResponse, HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    @CreateNewRoomApiDocs
    public ResponseEntity<RoomResponse> createRoom(@RequestBody RoomRequest request) {

        Room room = RoomConverter.convertToRoom(request);

        roomService.save(room);

        RoomResponse roomResponse = RoomConverter.convertToRoomResponse(room);

        return new ResponseEntity<>(roomResponse, HttpStatus.CREATED);
    }

    //@PreAuthorize("hasAnyRole('ROLE_CLIENT')")
    @GetMapping(value = "/{id}")
    @FindRoomByIdApiDocs
    public ResponseEntity<RoomResponse> findRoomById(@PathVariable Long id) {

        RoomResponse room = RoomConverter.convertToRoomResponse(roomService.findRoomById(id));

        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    @UpdateRoomApiDocs
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long id, @RequestBody RoomRequest request) {

        Room room = roomService.updateRoom(id, request);

        RoomResponse response = RoomConverter.convertToRoomResponse(room);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    @DeleteRoomByIdApiDocs
    public ResponseEntity<String> deleteById(@PathVariable Long id) {

        try {
            roomService.deleteById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {

            throw new RoomNotFoundException(String.format(ROOM_NOT_FOUND, id));
        }
    }

    //@PreAuthorize("hasAnyRole('ROLE_CLIENT')")
    @GetMapping
    @GetAvailableRooms
    public ResponseEntity<List<RoomResponse>> getAvailableRoomsByPeriodAndGuests(@RequestBody AvailableRoomRequest request) {

        List<Room> room = roomReservationService.findRoomByPeriodAndPeople(request.getStartDate(), request.getEndDate(),
                request.getAdults(), request.getKids());

        List<RoomResponse> rooms = room.stream().map(RoomConverter::convertToRoomResponse).collect(Collectors.toList());

        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyRole('ROLE_CLIENT')")
    @DeleteMapping(value = "/{id}/reservations/{rid}")
    @DeleteReservationByIdAndRoomIdApiDocs
    public ResponseEntity<HttpStatus> deleteReservationByIdAndRoomId(@PathVariable Long id, @PathVariable Long rid) {

        try {
            roomReservationService.deleteByRoomIdAndReservationId(id, rid);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {

            throw new RuntimeException("Reservation can not be deleted");
        }
    }
}
