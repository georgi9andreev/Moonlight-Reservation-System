package com.bootcamp3.MoonlightHotelAndSpa.converter;

import com.bootcamp3.MoonlightHotelAndSpa.dto.UserDto;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;

public class UserConverter {

    public static UserDto convertToUserDto(User user) {

        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setRoles(user.getRoles());
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setReservations(user.getReservations());

        return userDto;
    }
}
