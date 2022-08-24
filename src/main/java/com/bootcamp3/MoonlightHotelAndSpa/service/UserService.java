package com.bootcamp3.MoonlightHotelAndSpa.service;

import com.bootcamp3.MoonlightHotelAndSpa.dto.user.EmailRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.user.PasswordResetRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.user.UserRequest;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;

import java.util.Set;

public interface UserService {

    User registerUser(UserRequest userRequest);

    User findUserById(Long id);

    Set<User> getUsers();

    User updateUser(Long id, UserRequest userRequest);

    void deleteUserById(Long id);

    void forgotPassword(EmailRequest emailRequest);

    void resetPassword(PasswordResetRequest request);
}
