package com.example.LMS.service;

import com.example.LMS.dto.NotificationRequestDTO;
import com.example.LMS.dto.NotificationResponseDTO;
import com.example.LMS.entity.Notification;
import com.example.LMS.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotificationServiceTests {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    private NotificationRequestDTO notificationRequestDTO;
    private Notification notification;

    @BeforeEach
    void setUp() {
        // Initialize test data
        notificationRequestDTO = new NotificationRequestDTO();
        notificationRequestDTO.setRecipientUsername("user123");
        notificationRequestDTO.setMessage("Your assignment has been graded!");
        notificationRequestDTO.setRole("Student");

        notification = new Notification();
        notification.setId(1L);
        notification.setRecipientUsername("user123");
        notification.setMessage("Your assignment has been graded!");
        notification.setRole("Student");
        notification.setRead(false);
        notification.setTimestamp(LocalDateTime.now());
    }

    @Test
    void testCreateNotification() {
        // Mock the behavior of the repository to save the notification
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        // Call the method to create a notification
        NotificationResponseDTO createdNotification = notificationService.createNotification(notificationRequestDTO);

        // Verify that the returned notification DTO is correct
        assertNotNull(createdNotification);
        assertEquals("Your assignment has been graded!", createdNotification.getMessage());
        assertEquals("user123", createdNotification.getRecipientUsername());
        assertFalse(createdNotification.isRead());

        // Verify that the save method was called once
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void testGetAllNotifications() {
        // Mock the behavior of the repository to return a list of notifications
        when(notificationRepository.findByRecipientUsername("user123")).thenReturn(Arrays.asList(notification));

        // Call the method to get all notifications for the user
        List<NotificationResponseDTO> notifications = notificationService.getAllNotifications("user123");

        // Verify that the notifications list is not empty
        assertNotNull(notifications);
        assertFalse(notifications.isEmpty());

        // Verify that the correct notification is in the list
        assertEquals("Your assignment has been graded!", notifications.get(0).getMessage());

        // Verify that the findByRecipientUsername method was called once
        verify(notificationRepository, times(1)).findByRecipientUsername("user123");
    }

    @Test
    void testGetUnreadNotifications() {
        // Mock the behavior of the repository to return a list of unread notifications
        when(notificationRepository.findByRecipientUsernameAndIsRead("user123", false)).thenReturn(Arrays.asList(notification));

        // Call the method to get unread notifications for the user
        List<NotificationResponseDTO> unreadNotifications = notificationService.getUnreadNotifications("user123");

        // Verify that the unread notifications list is not empty
        assertNotNull(unreadNotifications);
        assertFalse(unreadNotifications.isEmpty());

        // Verify that the correct notification is in the list
        assertEquals("Your assignment has been graded!", unreadNotifications.get(0).getMessage());

        // Verify that the findByRecipientUsernameAndIsRead method was called once
        verify(notificationRepository, times(1)).findByRecipientUsernameAndIsRead("user123", false);
    }

    @Test
    void testMarkAsRead() {
        // Mock the behavior of the repository to return a notification
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

        // Call the method to mark the notification as read
        notificationService.markAsRead(1L);

        // Verify that the notification's 'read' status was updated
        assertTrue(notification.isRead());

        // Verify that the save method was called once to persist the updated notification
        verify(notificationRepository, times(1)).save(notification);

        // Verify that the findById method was called once
        verify(notificationRepository, times(1)).findById(1L);
    }

    @Test
    void testMarkAsRead_NotificationNotFound() {
        // Mock the behavior of the repository to return an empty Optional
        when(notificationRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the method and expect an exception to be thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            notificationService.markAsRead(1L);
        });

        // Verify that the exception message is correct
        assertEquals("Notification not found!", exception.getMessage());

        // Verify that the findById method was called once
        verify(notificationRepository, times(1)).findById(1L);
    }
}
