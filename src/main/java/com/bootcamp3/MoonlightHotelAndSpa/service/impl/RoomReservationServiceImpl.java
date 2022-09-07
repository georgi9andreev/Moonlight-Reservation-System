package com.bootcamp3.MoonlightHotelAndSpa.service.impl;

import com.bootcamp3.MoonlightHotelAndSpa.converter.RoomConverter;
import com.bootcamp3.MoonlightHotelAndSpa.dto.room.RoomResponse;
import com.bootcamp3.MoonlightHotelAndSpa.enumeration.RoomType;
import com.bootcamp3.MoonlightHotelAndSpa.enumeration.RoomView;
import com.bootcamp3.MoonlightHotelAndSpa.exception.RecordNotFoudException;
import com.bootcamp3.MoonlightHotelAndSpa.model.Room;
import com.bootcamp3.MoonlightHotelAndSpa.model.RoomReservation;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import com.bootcamp3.MoonlightHotelAndSpa.model.errormessage.ReservationErrorMessage;
import com.bootcamp3.MoonlightHotelAndSpa.repository.RoomReservationRepository;
import com.bootcamp3.MoonlightHotelAndSpa.service.RoomReservationService;
import com.bootcamp3.MoonlightHotelAndSpa.service.RoomService;
import com.bootcamp3.MoonlightHotelAndSpa.service.UserService;
import com.bootcamp3.MoonlightHotelAndSpa.validator.ReservationParametersValidator;
import com.bootcamp3.MoonlightHotelAndSpa.validator.RoomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

        ReservationParametersValidator.validateNumberOfGuests(adults, kids);
        ReservationParametersValidator.validateDates(startDate, endDate);

        List<Room> foundRooms = roomReservationRepository.findRoomByPeriodAndPeople(startDate, endDate, (adults + kids));

        RoomValidator.validateRoomResults(foundRooms);

        return foundRooms;
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

    @Override
    public ResponseEntity<?> filterRoomsByViewAndType(Instant startDate, Instant endDate, int adults, int kids, RoomView view, RoomType roomType) {

        List<Room> rooms = findRoomByPeriodAndPeople(startDate, endDate, adults, kids);
        ReservationParametersValidator.validatePeopleByRoomType(adults, kids, roomType);

        Predicate<Room> isViewEquals = r -> r.getRoomView().equals(view);
        Predicate<Room> isTypeEquals = r -> r.getTitle().equals(roomType);

        List<Room> filteredRooms = rooms
                .stream()
                .filter(isViewEquals.and(isTypeEquals))
                .collect(Collectors.toList());

        if (filteredRooms.size() == 0) {

            Set<String> errorMessages = new HashSet<>();

            List<Room> filterByView = filterBy(rooms, isViewEquals);

            if (filterByView.size() == 0) {
                errorMessages.add("Room with the selected view is not available for the specified dates");
            }

            List<Room> filterByRoomType = filterBy(filterByView, isTypeEquals);

            if (filterByRoomType.size() == 0) {
                errorMessages.add("Room with the selected type is not available for the specified dates");
            }

            return new ResponseEntity<>(createRoomReservationMessageResponse(errorMessages), HttpStatus.OK);
        }

        List<RoomResponse> roomResponse = filteredRooms.stream().map(RoomConverter::convertToRoomResponse).collect(Collectors.toList());
        return new ResponseEntity<>(roomResponse, HttpStatus.OK);
    }

    private List<Room> filterBy(List<Room> rooms, Predicate<Room> predicate) {

        return rooms
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    private ReservationErrorMessage createRoomReservationMessageResponse(Set<String> message) {

        return new ReservationErrorMessage("Reservation error", message);
    }
}
