package com.bootcamp3.MoonlightHotelAndSpa;

import com.bootcamp3.MoonlightHotelAndSpa.exception.RecordNotFoudException;
import com.bootcamp3.MoonlightHotelAndSpa.model.RoomReservation;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import com.bootcamp3.MoonlightHotelAndSpa.repository.RoomReservationRepository;
import com.bootcamp3.MoonlightHotelAndSpa.service.RoomService;
import com.bootcamp3.MoonlightHotelAndSpa.service.UserService;
import com.bootcamp3.MoonlightHotelAndSpa.service.impl.RoomReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RoomReservationServiceImplTest {

    @Mock
    private RoomReservationRepository roomReservationRepository;

    @Mock
    private UserService userService;

    private RoomService roomService;

    @Mock
    private RoomReservationServiceImpl roomReservationService;

    @BeforeEach
    public void setUp() {
        roomReservationService = new RoomReservationServiceImpl(roomReservationRepository, userService, roomService);
    }

    @Test
    public void verifySave() {

        RoomReservation roomReservation = RoomReservation.builder().build();
        roomReservationRepository.save(roomReservation);
        verify(roomReservationRepository, times(1)).save(roomReservation);
    }

    @Test
    public void verifyGetByUser() {
        RoomReservation roomReservation = RoomReservation.builder().build();
        User user = User.builder().build();
        roomReservation.setUser(user);
        roomReservationRepository.findAll();
        // verify(roomReservationRepository,times(1)).findAll(Set.of(roomReservation));
    }

    @Test
    public void verifyGetAll() {
        roomReservationRepository.findAll();
        verify(roomReservationRepository, times(1)).findAll();
    }

    @Test
    public void verifyFindRoomByPeriodAndPeople() {
        Instant startDate = Instant.now();
        Instant endDate = Instant.now();
        int adults = 2;
        int kids = 3;
        roomReservationRepository.findRoomByPeriodAndPeople(startDate, endDate, (adults + kids));
        verify(roomReservationRepository,times(1))
                .findRoomByPeriodAndPeople(startDate, endDate, (adults + kids));
    }

    @Test
    public void verifyFindReservationByIdAndUserIdSuccess () {

        Long uid = 2L;
        Long rid = 10L;

        com.bootcamp3.MoonlightHotelAndSpa.model.User userById = userService.findUserById(uid);
        verify(userService,times(1)).findUserById(uid);

        Optional<RoomReservation> roomReservation = roomReservationRepository.findById(rid);
        verify(roomReservationRepository, times(1)).findById(rid);

        roomReservation = Optional.ofNullable(RoomReservation.builder().user(User.builder().id(uid).build()).build());

        Long id = roomReservation.orElseThrow().getUser().getId();

        assertEquals(id, uid);
    }

    @Test
    public void verifyFindByIDSuccess () {
        Long id = 1L;
        roomReservationRepository.findById(id);
        verify(roomReservationRepository,times(1)).findById(id);
    }

    @Test
    public void verifyFindByIDThrowsException () {
        assertThrows(RecordNotFoudException.class,
                () -> roomReservationService.findById(null));
    }
}
