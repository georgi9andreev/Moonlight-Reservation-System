package com.bootcamp3.MoonlightHotelAndSpa.controller;

import com.bootcamp3.MoonlightHotelAndSpa.converter.UserConverter;
import com.bootcamp3.MoonlightHotelAndSpa.dto.UserResponse;
import com.bootcamp3.MoonlightHotelAndSpa.model.AuthenticationRequest;
import com.bootcamp3.MoonlightHotelAndSpa.model.AuthenticationResponse;
import com.bootcamp3.MoonlightHotelAndSpa.service.impl.LoginService;
import com.bootcamp3.MoonlightHotelAndSpa.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class LoginController {

    private final LoginService loginService;
    private final UserServiceImpl userService;

    @Autowired
    public LoginController(LoginService loginService, UserServiceImpl userService) {
        this.loginService = loginService;
        this.userService = userService;
    }

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        String token = loginService.login(authenticationRequest);
        UserResponse user = UserConverter
                .convertToUserDto(userService.loadUserByUsername(authenticationRequest.getUsername()));

        return new ResponseEntity<>(new AuthenticationResponse(token, user), HttpStatus.OK);
    }
}
