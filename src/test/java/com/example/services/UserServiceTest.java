package com.example.services;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        User mockUser = new User(1, "Alice");
        when(userRepository.findById(1)).thenReturn(mockUser);
        when(userRepository.findById(2)).thenReturn(mockUser);
        when(userRepository.findById(3)).thenReturn(null);
    }

    @Test
    void testGetUserName_UserFound() {
        String result = userService.getUserName(2);

        assertEquals("Alice", result);

        verify(userRepository).findById(2);
    }

    @Test
    void testGetUserName_UserNotFound() {
        // Arrange
        String result = userService.getUserName(3);

        assertEquals("User not found", result);
        verify(userRepository).findById(3);
    }

    @Test
    void testSaveUser() {
        User newUser = new User(2, "Bob");

        userService.saveUser(newUser);

        verify(userRepository).save(newUser); 
    }
}