package com.backendcats.controller;

import com.backendcats.dto.BreedDto;
import com.backendcats.model.User;
import com.backendcats.service.BreedService;
import com.backendcats.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setDocument("123");
        user.setPassword("password");
    }

    @Test
    void TestLogin() {
        when(userService.LoginUser(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userController.login(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService).LoginUser(any(User.class));
    }

    @Test
    void TestValidateToken() {
        ResponseEntity<Boolean> response = userController.ValidateToken();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }

    @Test
    void TestGetAllUsers() {
        List<User> users = Arrays.asList(user);
        when(userService.GetAllUser()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.GetAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
        verify(userService).GetAllUser();
    }

    @Test
    void TestSaveUser() {
        when(userService.SaveUser(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userController.SaveUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService).SaveUser(any(User.class));
    }

    @Test
    void TestDeleteUser() {
        when(userService.DeleteUser("123")).thenReturn(true);

        ResponseEntity<Boolean> response = userController.DeleteUser("123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(userService).DeleteUser("123");
    }
}
