package com.bootcamp3.MoonlightHotelAndSpa.service.impl;

import com.bootcamp3.MoonlightHotelAndSpa.exception.RecordNotFoudException;
import com.bootcamp3.MoonlightHotelAndSpa.model.Room;
import com.bootcamp3.MoonlightHotelAndSpa.model.RoomReservation;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import com.bootcamp3.MoonlightHotelAndSpa.repository.RoomReservationRepository;
import com.bootcamp3.MoonlightHotelAndSpa.service.RoomReservationService;
import com.bootcamp3.MoonlightHotelAndSpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class RoomReservationServiceImpl implements RoomReservationService {

    private final RoomReservationRepository roomReservationRepository;
    private final UserService userService;

    @Autowired
    public RoomReservationServiceImpl(RoomReservationRepository roomReservationRepository, UserService userService) {
        this.roomReservationRepository = roomReservationRepository;
        this.userService = userService;
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
                .orElseThrow(() -> new RecordNotFoudException(String.format("Reservation with id: %s, not found", id)));
    }


}
