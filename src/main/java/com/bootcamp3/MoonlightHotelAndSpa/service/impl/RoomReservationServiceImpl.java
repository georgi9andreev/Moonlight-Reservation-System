package com.bootcamp3.MoonlightHotelAndSpa.service.impl;

import com.bootcamp3.MoonlightHotelAndSpa.exception.RecordNotFoudException;
import com.bootcamp3.MoonlightHotelAndSpa.model.Room;
import com.bootcamp3.MoonlightHotelAndSpa.model.RoomReservation;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import com.bootcamp3.MoonlightHotelAndSpa.repository.RoomReservationRepository;
import com.bootcamp3.MoonlightHotelAndSpa.service.RoomReservationService;
import com.bootcamp3.MoonlightHotelAndSpa.service.RoomService;
import com.bootcamp3.MoonlightHotelAndSpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static com.bootcamp3.MoonlightHotelAndSpa.constant.ExceptionConstant.ROOM_RESERVATION_NOT_FOUND;

@Service
public class RoomReservationServiceImpl implements RoomReservationService {

    private final RoomReservationRepository roomReservationRepository;
    private final UserService userService;
    private final RoomService roomService;

    @Autowired
    public RoomReservationServiceImpl(RoomReservationRepository roomReservationRepository, UserService userService, RoomService roomService) {
        this.roomReservationRepository = roomReservationRepository;
        this.userService = userService;
        this.roomService = roomService;
    }

    @Override
    public void save(RoomReservation roomReservation) {

        roomReservationRepository.save(roomReservation);
    }

    @Override
    public List<RoomReservation> getByUser(User user) {
        RoomReservation roomReservation = new RoomReservation();
        roomReservation.setUser(user);
        return roomReservationRepository.findAll(Example.of(roomReservation));
    }

    @Override
    public List<RoomReservation> getAll() {
        return roomReservationRepository.findAll();
    }

    @Override
    public List<Room> findRoomByPeriodAndPeople(Instant startDate, Instant endDate, int adults, int kids) {
        return roomReservationRepository.findRoomByPeriodAndPeople(startDate, endDate, (adults + kids));
    }

    @Override
    public RoomReservation findReservationByIdAndUserId(Long uid, Long rid) {

        User user = userService.findUserById(uid);
        RoomReservation roomReservation = findById(rid);

        if (!roomReservation.getUser().getId().equals(uid)) {
            throw new RuntimeException("User id does not match to reservation");
        }

        return roomReservation;
    }

    @Override
    public RoomReservation findById(Long id) {

        return roomReservationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoudException(String.format(ROOM_RESERVATION_NOT_FOUND, id)));
    }

    @Override
    public void deleteByRoomIdAndReservationId(Long id, Long rid) {

        RoomReservation roomReservation = findById(rid);
        Room room = roomService.findRoomById(id);

        if (!id.equals(roomReservation.getRoom().getId())) {

            throw new RuntimeException("Room id does not match to reservation");
        }
    }


}
