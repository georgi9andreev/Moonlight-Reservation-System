package com.bootcamp3.MoonlightHotelAndSpa.converter;

import com.bootcamp3.MoonlightHotelAndSpa.dto.RoomReservation.RoomReservationRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.RoomReservation.RoomReservationResponse;
import com.bootcamp3.MoonlightHotelAndSpa.dto.user.UserReservationRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.user.UserReservationResponse;
import com.bootcamp3.MoonlightHotelAndSpa.dto.user.UserResponse;
import com.bootcamp3.MoonlightHotelAndSpa.dto.room.RoomResponse;
import com.bootcamp3.MoonlightHotelAndSpa.enumeration.PaymentStatus;
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

        Double totalPrice = calculatePrice(calculateDays(startDate, endDate), room.getPrice());

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

        int daysPeriod = calculateDays(roomReservation.getCheckIn(), roomReservation.getCheckOut());
        Double totalPrice = calculatePrice(daysPeriod, room.getPrice());

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

    public static UserReservationResponse convertToUserReservationResponse(RoomReservation roomReservation) {

        User user = roomReservation.getUser();
        UserResponse userResponse = UserConverter.convertToUserResponse(user);

        Room room = roomReservation.getRoom();
        RoomResponse roomResponse = RoomConverter.convertToRoomResponse(room);

        int daysPeriod = calculateDays(roomReservation.getCheckIn(), roomReservation.getCheckOut());
        Double totalPrice = calculatePrice(daysPeriod, room.getPrice());

        UserReservationResponse userReservationResponse = new UserReservationResponse();
        userReservationResponse.setId(roomReservation.getId());
        userReservationResponse.setAdults(roomReservation.getAdults());
        userReservationResponse.setKids(roomReservation.getKids());
        userReservationResponse.setStart_date(roomReservation.getCheckIn().toString());
        userReservationResponse.setEnd_date(roomReservation.getCheckOut().toString());
        userReservationResponse.setDays(daysPeriod);
        userReservationResponse.setType_bed(roomReservation.getFacilities());
        userReservationResponse.setView(room.getRoomView());
        userReservationResponse.setPrice(totalPrice);
        userReservationResponse.setDate(roomReservation.getCreatedAt().toString());
        userReservationResponse.setStatus("paid");
        userReservationResponse.setRoom(roomResponse);
        userReservationResponse.setUser(userResponse);

        return userReservationResponse;
    }

    public static RoomReservation update(Room room, RoomReservation roomReservation, UserReservationRequest userReservationRequest) {

        User user = userService.findUserById(userReservationRequest.getUser());

        Instant startDate = Instant.parse(userReservationRequest.getStart_date());
        Instant endDate = Instant.parse(userReservationRequest.getEnd_date());

        int days = calculateDays(startDate, endDate);
        double totalPrice = calculatePrice(days, room.getPrice());

        roomReservation.setCheckIn(startDate);
        roomReservation.setCheckOut(endDate);
        roomReservation.setAdults(userReservationRequest.getAdults());
        roomReservation.setKids(userReservationRequest.getKids());
        roomReservation.setStatus(PaymentStatus.PAID);
        roomReservation.setFacilities(userReservationRequest.getType_bed());
        roomReservation.setTotalPrice(totalPrice);
        roomReservation.setRoom(room);
        roomReservation.setUser(user);

        return roomReservation;
    }

    public static Integer calculateDays(Instant startDate, Instant endDate) {

        Long duration = Duration.between(startDate, endDate).toDays();

        return duration.intValue();
    }

    private static double calculatePrice(int days, double price) {

        return days * price;
    }
}
