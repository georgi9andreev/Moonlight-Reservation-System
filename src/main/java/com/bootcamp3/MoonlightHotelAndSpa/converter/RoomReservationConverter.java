package com.bootcamp3.MoonlightHotelAndSpa.converter;

import com.bootcamp3.MoonlightHotelAndSpa.dto.RoomReservation.RoomReservationRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.RoomReservation.RoomReservationResponse;
import com.bootcamp3.MoonlightHotelAndSpa.dto.room.RoomResponse;
import com.bootcamp3.MoonlightHotelAndSpa.model.Room;
import com.bootcamp3.MoonlightHotelAndSpa.model.RoomReservation;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import com.bootcamp3.MoonlightHotelAndSpa.service.impl.RoomServiceImpl;
import com.bootcamp3.MoonlightHotelAndSpa.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class RoomReservationConverter {

    private static UserServiceImpl userService;
    private static RoomServiceImpl roomService;

    @Autowired
    public RoomReservationConverter(UserServiceImpl userService, RoomServiceImpl roomService) {

        RoomReservationConverter.userService = userService;
        RoomReservationConverter.roomService = roomService;
    }

    public static RoomReservation convertToRoomReservation(Long id, RoomReservationRequest request) {

        User user = userService.findUserById(request.getUser());
        Room room = roomService.findRoomById(id);

        Instant startDate = Instant.parse(request.getStart_date());
        Instant endDate = Instant.parse(request.getEnd_date());

        Double totalPrice = calculateDays(startDate, endDate) * room.getPrice();

        RoomReservation roomReservation = new RoomReservation();
        roomReservation.setCreatedAt(Instant.now());
        roomReservation.setCheckIn(startDate);
        roomReservation.setCheckOut(endDate);
        roomReservation.setAdults(request.getAdults());
        roomReservation.setKids(request.getKids());
        roomReservation.setUser(user);
        roomReservation.setFacilities(request.getType_bed());
        roomReservation.setTotalPrice(totalPrice);
        roomReservation.setRoom(room);

        return roomReservation;
    }

    public static RoomReservationResponse convertToRoomReservationResponse(Long id, RoomReservation roomReservation) {

        Room room = roomService.findRoomById(id);
        RoomResponse roomResponse = RoomConverter.convertToRoomResponse(room);

        Double totalPrice = calculateDays(roomReservation.getCheckIn(), roomReservation.getCheckOut()) * room.getPrice();
        int daysPeriod = calculateDays(roomReservation.getCheckIn(), roomReservation.getCheckOut());

        RoomReservationResponse roomReservationResponse = new RoomReservationResponse();
        roomReservationResponse.setId(roomReservation.getId());
        roomReservationResponse.setStart_date(roomReservation.getCheckIn().toString());
        roomReservationResponse.setEnd_date(roomReservation.getCheckOut().toString());
        roomReservationResponse.setDays(daysPeriod);
        roomReservationResponse.setAdults(roomReservation.getAdults());
        roomReservationResponse.setKids(roomReservation.getKids());
        roomReservationResponse.setPrice(totalPrice);
        roomReservationResponse.setRoom(roomResponse);

        return roomReservationResponse;
    }

    private static Integer calculateDays(Instant startDate, Instant endDate) {

        Long duration = Duration.between(startDate, endDate).toDays();

        return duration.intValue();
    }
}
