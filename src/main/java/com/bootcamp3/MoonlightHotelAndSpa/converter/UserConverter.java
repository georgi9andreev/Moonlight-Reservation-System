package com.bootcamp3.MoonlightHotelAndSpa.converter;

import com.bootcamp3.MoonlightHotelAndSpa.dto.UserDto;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;

public class UserConverter {

    public static UserDto convertToUserDto(User user) {

        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getFirstName());
        userDto.setSurname(user.getLastName());
        userDto.setPhone(user.getPhoneNumber());
        userDto.setRoles(RolePrefixConverter.removePrefix(user.getAuthorityName()));
        userDto.setCreated(user.getCreatedAt());

        return userDto;
    }
}
