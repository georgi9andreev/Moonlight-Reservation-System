package com.bootcamp3.MoonlightHotelAndSpa.service;

import com.bootcamp3.MoonlightHotelAndSpa.dto.user.UserReservationRequest;
import com.bootcamp3.MoonlightHotelAndSpa.enumeration.RoomType;
import com.bootcamp3.MoonlightHotelAndSpa.enumeration.RoomView;
import com.bootcamp3.MoonlightHotelAndSpa.model.Room;
import com.bootcamp3.MoonlightHotelAndSpa.model.RoomReservation;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;

public interface RoomReservationService {

    void save(RoomReservation roomReservation);

    List<RoomReservation> getByUser(User user);

    List<RoomReservation> getAll();

    List<Room> findRoomByPeriodAndPeople(Instant startDate, Instant endDate, int adults, int kids);

    RoomReservation findReservationByIdAndUserId(Long uid, Long rid);

    RoomReservation findById(Long id);

    void deleteByRoomIdAndReservationId(Long id, Long rid);

    ResponseEntity<?> filterRoomsByViewAndType(Instant startDate, Instant endDate, int adults, int kids, RoomView view, RoomType roomType);

    List<RoomReservation> getReservationsByRoomId(Long id);

    RoomReservation getRoomReservationByIdAndRoomId(Long id, Long rid);

    RoomReservation updateRoomReservationByIdAndRoomId(Long id, Long rid, UserReservationRequest userReservationRequest);
}
