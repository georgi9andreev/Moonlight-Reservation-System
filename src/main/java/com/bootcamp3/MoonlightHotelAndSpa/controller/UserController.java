package com.bootcamp3.MoonlightHotelAndSpa.controller;

import com.bootcamp3.MoonlightHotelAndSpa.converter.UserConverter;
import com.bootcamp3.MoonlightHotelAndSpa.dto.UserRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.UserResponse;
import com.bootcamp3.MoonlightHotelAndSpa.exception.UserNotFoundException;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import com.bootcamp3.MoonlightHotelAndSpa.service.impl.EmailServiceImpl;
import com.bootcamp3.MoonlightHotelAndSpa.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

import static com.bootcamp3.MoonlightHotelAndSpa.constant.EmailConstant.*;
import static com.bootcamp3.MoonlightHotelAndSpa.constant.ExceptionConstant.BAD_CREDENTIALS;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final EmailServiceImpl emailService;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl, EmailServiceImpl emailService) {
        this.userServiceImpl = userServiceImpl;
        this.emailService = emailService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest) {
        User newUser =  userServiceImpl.register(userRequest);

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

            return new ResponseEntity<>(HttpStatus.OK);
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
}
