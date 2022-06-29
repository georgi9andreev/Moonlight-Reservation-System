package com.bootcamp3.MoonlightHotelAndSpa.controller;

import com.bootcamp3.MoonlightHotelAndSpa.converter.UserConverter;
import com.bootcamp3.MoonlightHotelAndSpa.dto.UserDto;
import com.bootcamp3.MoonlightHotelAndSpa.dto.UserRequest;
import com.bootcamp3.MoonlightHotelAndSpa.exception.UserNotFoundException;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import com.bootcamp3.MoonlightHotelAndSpa.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

import static com.bootcamp3.MoonlightHotelAndSpa.constant.ExceptionConstant.BAD_CREDENTIALS;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserRequest userRequest) {
        User newUser =  userServiceImpl.register(userRequest.getName(), userRequest.getSurname(),
                userRequest.getEmail(), userRequest.getPhone(), userRequest.getPassword(), userRequest.getRoles());

        UserDto responseUser = UserConverter.convertToUserDto(newUser);
        return new ResponseEntity<>(responseUser, HttpStatus.CREATED);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {

        try {
            userServiceImpl.deleteUserById(id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {

            throw new UserNotFoundException(BAD_CREDENTIALS);
        }
    }

    @Secured("admin")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {

        UserDto user = UserConverter.convertToUserDto(userServiceImpl.findUserById(id));

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Set<UserDto>> findAll() {
        Set<UserDto> userDtos = new HashSet<>();

        for (User user : userServiceImpl.getUsers()) {
            userDtos.add(UserConverter.convertToUserDto(user));
        }

        return ResponseEntity.ok(userDtos);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserRequest userRequest) {

        UserDto user = UserConverter.convertToUserDto(userServiceImpl.updateUser(id, userRequest));

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
