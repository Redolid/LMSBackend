package com.example.LMS.controller;


import com.example.LMS.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.example.LMS.dto.UserRequestDTO;
import com.example.LMS.dto.UserResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    


    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO user = userService.createUser(userRequestDTO);
        return ResponseEntity.ok(user);
    }
}

