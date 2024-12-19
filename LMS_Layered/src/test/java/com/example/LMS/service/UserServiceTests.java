package com.example.LMS.service;

import com.example.LMS.dto.UserRequestDTO;
import com.example.LMS.dto.UserResponseDTO;
import com.example.LMS.entity.User;
import com.example.LMS.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        // Initialize test data
        user = new User();
        user.setId(1L);
        user.setUsername("john_doe");
        user.setPassword("password123");
        user.setRole("USER");
        user.setEmail("john.doe@example.com");

        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("john_doe");
        userRequestDTO.setPassword("password123");
        userRequestDTO.setRole("USER");
        userRequestDTO.setEmail("john.doe@example.com");

        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(1L);
        userResponseDTO.setUsername("john_doe");
        userResponseDTO.setRole("USER");
        userResponseDTO.setEmail("john.doe@example.com");
    }

    @Test
    void testGetAllUsers() {
        // Mock the behavior of the repository to return a list of users
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        // Call the method to get all users
        List<UserResponseDTO> users = userService.getAllUsers();

        // Verify that the users list is not empty
        assertNotNull(users);
        assertEquals(1, users.size());

        // Verify that the user is correctly converted to a DTO
        assertEquals("john_doe", users.get(0).getUsername());
        assertEquals("USER", users.get(0).getRole());

        // Verify that the repository method was called once
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testCreateUserWithDTO() {
        // Mock the behavior of the repository to save the user
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Call the method to create a user
        UserResponseDTO createdUser = userService.createUser(userRequestDTO);

        // Verify that the returned user response DTO is correct
        assertNotNull(createdUser);
        assertEquals("john_doe", createdUser.getUsername());
        assertEquals("USER", createdUser.getRole());
        assertEquals("john.doe@example.com", createdUser.getEmail());

        // Verify that the repository method was called once
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUserWithEntity() {
        // Mock the behavior of the repository to save the user
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Call the method to create a user with a User entity
        User savedUser = userService.createUser(user);

        // Verify that the returned user is correct
        assertNotNull(savedUser);
        assertEquals("john_doe", savedUser.getUsername());
        assertEquals("USER", savedUser.getRole());
        assertEquals("john.doe@example.com", savedUser.getEmail());

        // Verify that the repository method was called once
        verify(userRepository, times(1)).save(any(User.class));
    }
}
