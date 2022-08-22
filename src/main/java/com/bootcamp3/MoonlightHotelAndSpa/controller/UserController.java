package com.bootcamp3.MoonlightHotelAndSpa.controller;

import com.bootcamp3.MoonlightHotelAndSpa.annotation.openapidocs.user.*;
import com.bootcamp3.MoonlightHotelAndSpa.converter.RoomReservationConverter;
import com.bootcamp3.MoonlightHotelAndSpa.converter.TableReservationConverter;
import com.bootcamp3.MoonlightHotelAndSpa.converter.UserConverter;
import com.bootcamp3.MoonlightHotelAndSpa.dto.*;
import com.bootcamp3.MoonlightHotelAndSpa.dto.restaurant.TableReservationResponse;
import com.bootcamp3.MoonlightHotelAndSpa.exception.UserNotFoundException;
import com.bootcamp3.MoonlightHotelAndSpa.model.RoomReservation;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import com.bootcamp3.MoonlightHotelAndSpa.model.table.TableReservation;
import com.bootcamp3.MoonlightHotelAndSpa.service.RoomReservationService;
import com.bootcamp3.MoonlightHotelAndSpa.service.TableReservationService;
import com.bootcamp3.MoonlightHotelAndSpa.service.impl.EmailServiceImpl;
import com.bootcamp3.MoonlightHotelAndSpa.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bootcamp3.MoonlightHotelAndSpa.constant.EmailConstant.EMAIL_SUBJECT;
import static com.bootcamp3.MoonlightHotelAndSpa.constant.EmailConstant.EMAIL_TEXT;
import static com.bootcamp3.MoonlightHotelAndSpa.constant.ExceptionConstant.BAD_CREDENTIALS;

@RestController
@RequestMapping(value = "/users", produces = "application/json")
@Tag(name = "Users")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final EmailServiceImpl emailService;
    private final RoomReservationService roomReservationService;
    private final TableReservationService tableReservationService;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl, EmailServiceImpl emailService, RoomReservationService roomReservationService, TableReservationService tableReservationService) {
        this.userServiceImpl = userServiceImpl;
        this.emailService = emailService;
        this.roomReservationService = roomReservationService;
        this.tableReservationService = tableReservationService;
    }

    @PostMapping("/forgot")
    @UserPasswordApiDocs(summary = "Send request to email for resetting user password")
    public ResponseEntity<HttpStatus> forgotPassword(@RequestBody EmailRequest emailRequest) {

        userServiceImpl.forgotPassword(emailRequest);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        //TO DO - Send email with link inside in format {domain-name}/reset/:token/:email
    }

    @PostMapping("/reset")
    @UserPasswordApiDocs(summary = "Reset password")
    public ResponseEntity<HttpStatus> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) {

        userServiceImpl.resetPassword(passwordResetRequest);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @RegisterUserApiDocs
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest userRequest) {
        User newUser = userServiceImpl.register(userRequest);

        UserResponse responseUser = UserConverter.convertToUserDto(newUser);

        String emailText = String.format(EMAIL_TEXT, userRequest.getName(), userRequest.getPassword());
        emailService.sendEmail(userRequest.getEmail(), EMAIL_SUBJECT, emailText);

        return new ResponseEntity<>(responseUser, HttpStatus.CREATED);
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    @DeleteUserApiDocs
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {

        try {
            userServiceImpl.deleteUserById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {

            throw new UserNotFoundException(BAD_CREDENTIALS);
        }
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    @FindUserByIdApiDocs
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long id) {

        UserResponse user = UserConverter.convertToUserDto(userServiceImpl.findUserById(id));

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping
    @FindListOfAllUsersApiDocs
    public ResponseEntity<Set<UserResponse>> getListOfAllUsers() {
        Set<UserResponse> userResponses = new HashSet<>();

        for (User user : userServiceImpl.getUsers()) {
            userResponses.add(UserConverter.convertToUserDto(user));
        }

        return ResponseEntity.ok(userResponses);
    }

    //@PreAuthorize("hasAnyRole('ROLE_CLIENT')")
    @PutMapping(value = "/{id}")
    @UserUpdateApiDocs
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {

        UserResponse user = UserConverter.convertToUserDto(userServiceImpl.updateUser(id, userRequest));

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyRole('ROLE_CLIENT')")
    @GetMapping(value = "/{id}/reservations")
    @FindReservationsByUserIdApiDocs
    public ResponseEntity<List<UserReservationResponse>> getReservationsByUserId(@PathVariable Long id) {
        User user = userServiceImpl.findUserById(id);
        List<RoomReservation> reservations = roomReservationService.getByUser(user);

        List<UserReservationResponse> userReservationResponses = reservations
                .stream()
                .map(RoomReservationConverter::convertToUserReservationResponse).collect(Collectors.toList());

        return new ResponseEntity<>(userReservationResponses, HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/reservations")
    @FindReservationsApiDocs
    public ResponseEntity<List<UserReservationResponse>> getReservations() {
        List<RoomReservation> reservations = roomReservationService.getAll();

        List<UserReservationResponse> reservationResponses = reservations
                .stream()
                .map(RoomReservationConverter::convertToUserReservationResponse).collect(Collectors.toList());

        return new ResponseEntity<>(reservationResponses, HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyRole('ROLE_CLIENT')")
    @GetMapping(value = "/{id}/reservations/{rid}")
    @FindReservationsByUserIdApiDocs
    public ResponseEntity<UserReservationResponse> getReservationByIdAndUserId(@PathVariable Long uid, @PathVariable Long rid) {

        RoomReservation roomReservation = roomReservationService.findReservationByIdAndUserId(uid, rid);

        UserReservationResponse userReservationResponse = RoomReservationConverter.convertToUserReservationResponse(roomReservation);

        return new ResponseEntity<>(userReservationResponse, HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyRole('ROLE_CLIENT')")
    @GetMapping(value = "/{id}/tables/reservations")
    @FindTableReservationsByUserApiDocs
    public ResponseEntity<List<TableReservationResponse>> getTableReservationsByUser(@PathVariable Long id) {

        List<TableReservation> tableReservations = tableReservationService.getTableReservationsByUser(id);

        List<TableReservationResponse> response = tableReservations
                .stream()
                .map(TableReservationConverter::convertToTableReservationResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
