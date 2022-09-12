package com.bootcamp3.MoonlightHotelAndSpa;

import com.bootcamp3.MoonlightHotelAndSpa.exception.UserNotFoundException;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;
import com.bootcamp3.MoonlightHotelAndSpa.repository.UserRepository;
import com.bootcamp3.MoonlightHotelAndSpa.service.UserService;
import com.bootcamp3.MoonlightHotelAndSpa.service.impl.EmailServiceImpl;
import com.bootcamp3.MoonlightHotelAndSpa.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @Mock
    private EmailServiceImpl emailService;

    @BeforeEach
    public void setUp() {
        userService  = new UserServiceImpl(userRepository, emailService);
    }

    @Test
    public void verifyUserRegistration() {

        String password = "1234";

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword(password);

        userRepository.save(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void verifyFindById() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.of(User.builder().build()));
        userService.findUserById(id);
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    public void verifyFindUserByIdThrowsException () {
        Long id = 5L;
        String expectedMessage = String.format("User with id %s not found", id);
        UserNotFoundException ex =assertThrows(UserNotFoundException.class,
                () -> userService.findUserById(id));

        assertEquals(expectedMessage, ex.getMessage());
    }

}
