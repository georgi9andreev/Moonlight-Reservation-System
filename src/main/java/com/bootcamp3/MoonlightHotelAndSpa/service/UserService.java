package com.bootcamp3.MoonlightHotelAndSpa.service;

import com.bootcamp3.MoonlightHotelAndSpa.dto.UserRequest;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;

import java.util.Set;

public interface UserService {

    User register(String firstName, String lastName, String email, String phoneNumber, String password,
                  Set<String> role);

    User findUserById(Long id);

    Set<User> getUsers();


    User updateUser(Long id, UserRequest userRequest);

    void deleteUserById(Long id);

//    void resetPassword(String email);
}
