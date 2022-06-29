package com.bootcamp3.MoonlightHotelAndSpa.service.impl;

import com.bootcamp3.MoonlightHotelAndSpa.converter.RolePrefixConverter;
import com.bootcamp3.MoonlightHotelAndSpa.dto.UserRequest;
import com.bootcamp3.MoonlightHotelAndSpa.exception.UserNotFoundException;
import com.bootcamp3.MoonlightHotelAndSpa.model.Role;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import com.bootcamp3.MoonlightHotelAndSpa.repository.UserRepository;
import com.bootcamp3.MoonlightHotelAndSpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static com.bootcamp3.MoonlightHotelAndSpa.constant.ExceptionConstant.BAD_CREDENTIALS;
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
    public User register(String firstName, String lastName, String email, String phoneNumber, String password,
                         Set<String> role) {

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(passwordEncoder(password));
        user.setCreatedAt(Instant.now());
        user.setRoles(convertRoleStringToRole(role));

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

        Set<Role> roles = new HashSet<>();

        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getName());
        user.setLastName(userRequest.getSurname());
        user.setPhoneNumber(userRequest.getPhone());
        user.setPassword(userRequest.getPassword());
        user.setRoles(convertRoleStringToRole(userRequest.getRoles()));
        user.setCreatedAt(Instant.now());

        userRepository.save(user);

        return user;
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User loadUserByUsername(String username) {

        return userRepository.findUserByEmail(username)
                .orElseThrow(() -> new BadCredentialsException(BAD_CREDENTIALS));
    }

    private Set<Role> convertRoleStringToRole(Set<String> role) {

        String roleString = role.iterator().next();
        Role foundRole = roleService.findRoleByAuthority(RolePrefixConverter.addPrefix(roleString));

        Set<Role> roles = new HashSet<>();
        roles.add(foundRole);

        return roles;
    }

    private User validateEmail(String email) {
        return null;
    }

    private String passwordEncoder(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
