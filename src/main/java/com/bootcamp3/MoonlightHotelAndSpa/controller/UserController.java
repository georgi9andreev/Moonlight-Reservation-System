package com.bootcamp3.MoonlightHotelAndSpa.controller;

import com.bootcamp3.MoonlightHotelAndSpa.converter.RoomReservationConverter;
import com.bootcamp3.MoonlightHotelAndSpa.converter.UserConverter;
import com.bootcamp3.MoonlightHotelAndSpa.dto.*;
import com.bootcamp3.MoonlightHotelAndSpa.exception.UserNotFoundException;
import com.bootcamp3.MoonlightHotelAndSpa.model.RoomReservation;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import com.bootcamp3.MoonlightHotelAndSpa.service.RoomReservationService;
import com.bootcamp3.MoonlightHotelAndSpa.service.impl.EmailServiceImpl;
import com.bootcamp3.MoonlightHotelAndSpa.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bootcamp3.MoonlightHotelAndSpa.constant.EmailConstant.*;
import static com.bootcamp3.MoonlightHotelAndSpa.constant.ExceptionConstant.BAD_CREDENTIALS;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final EmailServiceImpl emailService;

    private final RoomReservationService roomReservationService;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl, EmailServiceImpl emailService, RoomReservationService roomReservationService) {
        this.userServiceImpl = userServiceImpl;
        this.emailService = emailService;
        this.roomReservationService = roomReservationService;
    }

    @PostMapping("/forgot")
    public ResponseEntity<HttpStatus> forgotPassword(@RequestBody EmailRequest emailRequest) {

        userServiceImpl.forgotPassword(emailRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reset")
    public ResponseEntity<HttpStatus> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) {

        userServiceImpl.resetPassword(passwordResetRequest);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest) {
        User newUser = userServiceImpl.register(userRequest);

        UserResponse responseUser = UserConverter.convertToUserDto(newUser);

        String emailText = String.format(EMAIL_TEXT, userRequest.getName(), userRequest.getPassword());
        emailService.sendEmail(userRequest.getEmail(), EMAIL_SUBJECT, emailText);

        return new ResponseEntity<>(responseUser, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {

        try {
            userServiceImpl.deleteUserById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {

            throw new UserNotFoundException(BAD_CREDENTIALS);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {

        UserResponse user = UserConverter.convertToUserDto(userServiceImpl.findUserById(id));

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Set<UserResponse>> findAll() {
        Set<UserResponse> userResponses = new HashSet<>();

        for (User user : userServiceImpl.getUsers()) {
            userResponses.add(UserConverter.convertToUserDto(user));
        }

        return ResponseEntity.ok(userResponses);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserRequest userRequest) {

        UserResponse user = UserConverter.convertToUserDto(userServiceImpl.updateUser(id, userRequest));

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/reservations")
    public ResponseEntity<List<UserReservationResponse>> getReservationsByUserId(@PathVariable Long id) {
        User user = userServiceImpl.findUserById(id);
        List<RoomReservation> reservations = roomReservationService.getByUser(user);

        List<UserReservationResponse> userReservationResponses = reservations
                .stream()
                .map(RoomReservationConverter::convertToUserReservationResponse).collect(Collectors.toList());

        return new ResponseEntity<>(userReservationResponses, HttpStatus.OK);
    }

    @GetMapping(value = "/reservations")
    public ResponseEntity<List<UserReservationResponse>> getReservations() {
        List<RoomReservation> reservations = roomReservationService.getAll();

        List<UserReservationResponse> reservationResponses = reservations
                .stream()
                .map(RoomReservationConverter::convertToUserReservationResponse).collect(Collectors.toList());

        return new ResponseEntity<>(reservationResponses, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/reservations/{rid}")
    private ResponseEntity<UserReservationResponse> getReservationByIdAndUserId(@PathVariable Long uid, @PathVariable Long rid) {

        RoomReservation roomReservation = roomReservationService.findReservationByIdAndUserId(uid, rid);

        UserReservationResponse userReservationResponse = RoomReservationConverter.convertToUserReservationResponse(roomReservation);

        return new ResponseEntity<>(userReservationResponse, HttpStatus.OK);
    }
}
