package com.backendcats.service;

import com.backendcats.model.User;
import com.backendcats.repository.UserRepository;
import com.backendcats.util.exception.ErrorException;
import com.backendcats.util.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Environment env;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private Logger logger;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setDocument("123");
        user.setPassword("password");
    }

    @Test
    void TestGetAllUser() {
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);

        var result = userService.GetAllUser();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void TestSaveUser() {
        when(userRepository.findById("123")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.SaveUser(user);

        assertNotNull(savedUser);
        assertEquals(user.getDocument(), savedUser.getDocument());
        verify(userRepository).findById("123");
        verify(userRepository).save(user);
    }

    @Test
    void TestFindUserById() {
        when(userRepository.findById("123")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.FindUserById("123");

        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
        verify(userRepository).findById("123");
    }

    @Test
    void TestDeleteUser() {
        doNothing().when(userRepository).deleteById("123");

        boolean result = userService.DeleteUser("123");

        assertTrue(result);
        verify(userRepository).deleteById("123");
    }

    @Test
    void TestLoginUser_Success() {
        when(userRepository.findById("123")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", user.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken("123")).thenReturn("token");

        User loggedInUser = userService.LoginUser(user);

        assertNotNull(loggedInUser);
        assertEquals("token", loggedInUser.getToken());
        verify(userRepository).findById("123");
    }

    @Test
    void TestLoginUser_UserNotFound() {
        when(userRepository.findById("123")).thenReturn(Optional.empty());

        ErrorException exception = assertThrows(ErrorException.class, () -> userService.LoginUser(user));
        assertEquals(env.getProperty("constans.request-error-login"), exception.getMessage());
    }

    @Test
    void TestLoginUser_InvalidPassword() {
        when(userRepository.findById("123")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", user.getPassword())).thenReturn(false);

        ErrorException exception = assertThrows(ErrorException.class, () -> userService.LoginUser(user));
        assertEquals(env.getProperty("constans.request-error-login"), exception.getMessage());
    }
}
