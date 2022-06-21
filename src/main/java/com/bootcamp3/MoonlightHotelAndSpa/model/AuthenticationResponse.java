package com.bootcamp3.MoonlightHotelAndSpa.model;

import com.bootcamp3.MoonlightHotelAndSpa.dto.UserDto;

public class AuthenticationResponse {

    private final String token;
    private final UserDto user;

    public AuthenticationResponse(String token, UserDto user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public UserDto getUser() {
        return user;
    }
}
