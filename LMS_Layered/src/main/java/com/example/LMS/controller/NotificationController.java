package com.example.LMS.controller;

import com.example.LMS.dto.NotificationRequestDTO;
import com.example.LMS.dto.NotificationResponseDTO;
import com.example.LMS.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    
    @GetMapping
    public String testEndpoint() {
        return "Testing, From Notification Component!";
    }

    @PostMapping
    public ResponseEntity<NotificationResponseDTO> createNotification(@RequestBody NotificationRequestDTO requestDTO) {
        NotificationResponseDTO notification = notificationService.createNotification(requestDTO);
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<NotificationResponseDTO>> getAllNotifications(@PathVariable String username) {
        List<NotificationResponseDTO> notifications = notificationService.getAllNotifications(username);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{username}/unread")
    public ResponseEntity<List<NotificationResponseDTO>> getUnreadNotifications(@PathVariable String username) {
        List<NotificationResponseDTO> unreadNotifications = notificationService.getUnreadNotifications(username);
        return ResponseEntity.ok(unreadNotifications);
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<String> markNotificationAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok("Notification marked as read.");
    }
}
