package com.example.LMS.controller;

import com.example.LMS.dto.NotificationRequestDTO;
import com.example.LMS.dto.NotificationResponseDTO;
import com.example.LMS.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
class NotificationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    private NotificationRequestDTO notificationRequestDTO;
    private NotificationResponseDTO notificationResponseDTO;

    @BeforeEach
    void setUp() {
        // Setting up test data
        notificationRequestDTO = new NotificationRequestDTO();
        notificationRequestDTO.setMessage("New notification message");
        notificationRequestDTO.setRecipientUsername("testUser");

        notificationResponseDTO = new NotificationResponseDTO();
        notificationResponseDTO.setId(1L);
        notificationResponseDTO.setMessage("New notification message");
        notificationResponseDTO.setRecipientUsername("testUser");
        notificationResponseDTO.setRead(false);
    }

    @Test
    void testTestEndpoint() throws Exception {
        mockMvc.perform(get("/api/notifications"))
                .andExpect(status().isOk())
                .andExpect(content().string("Testing, From Notification Component!"));
    }

    @Test
    void testCreateNotification() throws Exception {
        when(notificationService.createNotification(any(NotificationRequestDTO.class))).thenReturn(notificationResponseDTO);

        mockMvc.perform(post("/api/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"message\":\"New notification message\",\"username\":\"testUser\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.message").value("New notification message"))
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.read").value(false));

        verify(notificationService, times(1)).createNotification(any(NotificationRequestDTO.class));
    }

    @Test
    void testGetAllNotifications() throws Exception {
        List<NotificationResponseDTO> notifications = Arrays.asList(notificationResponseDTO);
        when(notificationService.getAllNotifications("testUser")).thenReturn(notifications);

        mockMvc.perform(get("/api/notifications/{username}", "testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].message").value("New notification message"))
                .andExpect(jsonPath("$[0].username").value("testUser"))
                .andExpect(jsonPath("$[0].read").value(false));

        verify(notificationService, times(1)).getAllNotifications("testUser");
    }

    @Test
    void testGetUnreadNotifications() throws Exception {
        List<NotificationResponseDTO> unreadNotifications = Arrays.asList(notificationResponseDTO);
        when(notificationService.getUnreadNotifications("testUser")).thenReturn(unreadNotifications);

        mockMvc.perform(get("/api/notifications/{username}/unread", "testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].message").value("New notification message"))
                .andExpect(jsonPath("$[0].username").value("testUser"))
                .andExpect(jsonPath("$[0].read").value(false));

        verify(notificationService, times(1)).getUnreadNotifications("testUser");
    }

    @Test
    void testMarkNotificationAsRead() throws Exception {
        doNothing().when(notificationService).markAsRead(1L);

        mockMvc.perform(put("/api/notifications/{id}/read", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Notification marked as read."));

        verify(notificationService, times(1)).markAsRead(1L);
    }
}
