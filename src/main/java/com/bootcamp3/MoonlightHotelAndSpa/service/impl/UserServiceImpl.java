package com.bootcamp3.MoonlightHotelAndSpa.service.impl;

import com.bootcamp3.MoonlightHotelAndSpa.configuration.PasswordEncoder;
import com.bootcamp3.MoonlightHotelAndSpa.converter.UserConverter;
import com.bootcamp3.MoonlightHotelAndSpa.dto.EmailRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.PasswordResetRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.UserRequest;
import com.bootcamp3.MoonlightHotelAndSpa.exception.UserNotFoundException;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import com.bootcamp3.MoonlightHotelAndSpa.repository.UserRepository;
import com.bootcamp3.MoonlightHotelAndSpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.bootcamp3.MoonlightHotelAndSpa.constant.EmailConstant.EMAIL_FORGOT_PASSWORD;
import static com.bootcamp3.MoonlightHotelAndSpa.constant.EmailConstant.EMAIL_FORGOT_PASSWORD_SUBJECT;
import static com.bootcamp3.MoonlightHotelAndSpa.constant.ExceptionConstant.*;
import static com.bootcamp3.MoonlightHotelAndSpa.constant.SecurityConstant.*;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final EmailServiceImpl emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EmailServiceImpl emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public User register(UserRequest userRequest) {

        User user = UserConverter.convertToUser(userRequest);
        userRepository.save(user);

        return user;
    }

    @Override
    public User findUserById(Long id) {
       return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND, id)));
    }

    @Override
    public Set<User> getUsers() {

        return new HashSet<>(userRepository.findAll());
    }

    @Override
    public User updateUser(Long id, UserRequest userRequest) {

        User user = findUserById(id);
        userRepository.save(UserConverter.convertToUser(userRequest));

        return user;
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void forgotPassword(EmailRequest emailRequest) {
        User user = loadUserByUsername(emailRequest.getEmail());
        String newPassword = generateToken();

        user.setPassword(PasswordEncoder.encodePassword(newPassword));
        userRepository.save(user);

        String message = String.format(EMAIL_FORGOT_PASSWORD,user.getFirstName(), newPassword);
        emailService.sendEmail(emailRequest.getEmail(), EMAIL_FORGOT_PASSWORD_SUBJECT, message);
    }

    @Override
    public void resetPassword(PasswordResetRequest request) {

        User user = loadUserByUsername(request.getEmail());

        if (PasswordEncoder.encoder().matches(request.getToken(), user.getPassword())) {

            String newPassword = PasswordEncoder.encoder().encode(request.getPassword());
            user.setPassword(newPassword);

            userRepository.save(user);
        } else {

            throw new BadCredentialsException("Token does not match");
        }
    }

    private String generateNewPassword() {

        String chars = RANDOM_PASSWORD_CHARS;

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(RANDOM_PASSWORD_LENGTH);

        for (int i = 0; i < RANDOM_PASSWORD_LENGTH; i++)  {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        return sb.toString();
    }

    private String generateToken() {

        return UUID.randomUUID().toString();
    }

    @Override
    public User loadUserByUsername(String username) {

        return userRepository.findUserByEmail(username)
                .orElseThrow(() -> new BadCredentialsException(BAD_CREDENTIALS));
    }
}
