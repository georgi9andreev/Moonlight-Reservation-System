package com.bootcamp3.MoonlightHotelAndSpa.service.impl;

import com.bootcamp3.MoonlightHotelAndSpa.exception.UserNotFoundException;
import com.bootcamp3.MoonlightHotelAndSpa.model.Role;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import com.bootcamp3.MoonlightHotelAndSpa.repository.UserRepository;
import com.bootcamp3.MoonlightHotelAndSpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

import static com.bootcamp3.MoonlightHotelAndSpa.constant.ExceptionConstant.USER_NOT_FOUND;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleServiceImpl roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleServiceImpl roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User register(String firstName, String lastName, String email, String phoneNumber, String password) {

        Role role = new Role();
        role.setAuthority("Client");

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(passwordEncoder(password));
        user.setCreatedAt(Instant.now());
        user.setRoles(Set.of(role));

        role.setUser(Set.of(user));
        roleService.save(role);

        userRepository.save(user);

        return user;
    }

    private String passwordEncoder(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public User loadUserByUsername(String username) {

        return userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }
}
