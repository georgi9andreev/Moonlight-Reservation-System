package com.bootcamp3.MoonlightHotelAndSpa.service;

import com.bootcamp3.MoonlightHotelAndSpa.model.User;

public interface UserService {

    User register(String firstName, String lastName, String email, String phoneNumber, String password);

//    User findUserById(Long id);
//
//    List<User> getUsers();
//
//    User findUserByEmail(String email) throws UserNotFoundException;
//
//    User updateUser(String newFirstName, String newLastName, String newEmail, String newPhoneNumber,
//                    String newPassword);
//
//    void deleteUser(Long id);
//
//    void resetPassword(String email);
}
