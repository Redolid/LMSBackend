package com.example.LMS.service;

import com.example.LMS.dto.NotificationRequestDTO;
import com.example.LMS.dto.NotificationResponseDTO;
import com.example.LMS.entity.Notification;
import com.example.LMS.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public NotificationResponseDTO createNotification(NotificationRequestDTO requestDTO) {
        Notification notification = new Notification();
        notification.setRecipientUsername(requestDTO.getRecipientUsername());
        notification.setMessage(requestDTO.getMessage());
        notification.setRole(requestDTO.getRole());
        notification.setTimestamp(LocalDateTime.now());

        Notification savedNotification = notificationRepository.save(notification);
        return convertToDTO(savedNotification);
    }

    public List<NotificationResponseDTO> getAllNotifications(String username) {
        List<Notification> notifications = notificationRepository.findByRecipientUsername(username);
        return notifications.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<NotificationResponseDTO> getUnreadNotifications(String username) {
        List<Notification> unreadNotifications = notificationRepository.findByRecipientUsernameAndIsRead(username, false);
        return unreadNotifications.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found!"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    private NotificationResponseDTO convertToDTO(Notification notification) {
        NotificationResponseDTO dto = new NotificationResponseDTO();
        dto.setId(notification.getId());
        dto.setRecipientUsername(notification.getRecipientUsername());
        dto.setMessage(notification.getMessage());
        dto.setRead(notification.isRead());
        dto.setRole(notification.getRole());
        dto.setTimestamp(notification.getTimestamp());
        return dto;
    }
}
