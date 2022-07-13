package com.bootcamp3.MoonlightHotelAndSpa.service;

import com.bootcamp3.MoonlightHotelAndSpa.dto.EmailRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.PasswordResetRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.UserRequest;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;

import java.util.Set;

public interface UserService {

    User register(UserRequest userRequest);

    User findUserById(Long id);

    Set<User> getUsers();

    User updateUser(Long id, UserRequest userRequest);

    void deleteUserById(Long id);

    void forgotPassword(EmailRequest emailRequest);

    void resetPassword(PasswordResetRequest request);
}
