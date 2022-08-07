package com.bootcamp3.MoonlightHotelAndSpa.validator;

import com.bootcamp3.MoonlightHotelAndSpa.exception.RecordNotFoudException;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import com.bootcamp3.MoonlightHotelAndSpa.repository.UserRepository;
import com.bootcamp3.MoonlightHotelAndSpa.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.bootcamp3.MoonlightHotelAndSpa.constant.ExceptionConstant.USER_NOT_FOUND;

@Component
public class UserValidator {

    @Autowired
    private final UserRepository userRepository;
    private final UserServiceImpl userService;

    public UserValidator(UserRepository userRepository, UserServiceImpl userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public void validateUserById(Long uid) {

        userRepository.findById(uid)
                .orElseThrow(() -> new RecordNotFoudException(USER_NOT_FOUND));
    }

    public void validateUserByEmail(String email) {

        userRepository.findUserByEmail(email)
                .orElseThrow(() -> new RecordNotFoudException(String.format("User with email: %s, not found", email)));
    }

    public void validateEmailDuplicates(String email) {

        User user = userService.loadUserByUsername(email);

        if (user != null) {
            throw new RuntimeException(String.format("Email: %s, already exist", email));
        }
    }
}
