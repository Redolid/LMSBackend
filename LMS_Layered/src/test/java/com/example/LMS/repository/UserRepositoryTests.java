package com.example.LMS.repository;

import com.example.LMS.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        // Set up users
        user1 = new User();
        user1.setUsername("john_doe");
        user1.setPassword("password123");
        user1.setRole("USER");
        user1.setEmail("john_doe@example.com");
        
        user2 = new User();
        user2.setUsername("jane_doe");
        user2.setPassword("password123");
        user2.setRole("ADMIN");
        user2.setEmail("jane_doe@example.com");

        // Save users to the repository
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    void testFindByUsername() {
        // Find user by username
        User foundUser = userRepository.findByUsername(user1.getUsername());

        assertNotNull(foundUser);
        assertEquals("john_doe", foundUser.getUsername());
        assertEquals("USER", foundUser.getRole());
    }

    @Test
    void testFindByUsernameNotFound() {
        // Try finding a user by a non-existing username
        User foundUser = userRepository.findByUsername("non_existing_user");

        assertNull(foundUser);  // No user should be found
    }

    @Test
    void testFindByEmail() {
        // Find user by email
        User foundUser = userRepository.findByEmail(user2.getEmail());

        assertNotNull(foundUser);
        assertEquals("jane_doe@example.com", foundUser.getEmail());
        assertEquals("ADMIN", foundUser.getRole());
    }

    @Test
    void testFindByEmailNotFound() {
        // Try finding a user by a non-existing email
        User foundUser = userRepository.findByEmail("non_existing_email@example.com");

        assertNull(foundUser);  // No user should be found
    }

    @Test
    void testSaveUser() {
        // Save a new user and verify the save operation
        User newUser = new User();
        newUser.setUsername("new_user");
        newUser.setPassword("password123");
        newUser.setRole("USER");
        newUser.setEmail("new_user@example.com");

        User savedUser = userRepository.save(newUser);

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals("new_user", savedUser.getUsername());
        assertEquals("new_user@example.com", savedUser.getEmail());
    }

    @Test
    void testDeleteUserById() {
        // Delete a user by ID
        userRepository.deleteById(user1.getId());

        // Verify that the user is deleted
        User deletedUser = userRepository.findById(user1.getId()).orElse(null);

        assertNull(deletedUser);  // The user should no longer exist
    }

    @Test
    void testFindAllUsers() {
        // Find all users and check the list size
        var users = userRepository.findAll();
        assertNotNull(users);
        assertEquals(2, users.size());  // We saved 2 users in setUp()
    }
}
