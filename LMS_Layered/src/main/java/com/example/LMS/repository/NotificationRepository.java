package com.example.LMS.repository;

import com.example.LMS.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipientUsernameAndIsRead(String recipientUsername, boolean isRead);
    List<Notification> findByRecipientUsername(String recipientUsername);
}
