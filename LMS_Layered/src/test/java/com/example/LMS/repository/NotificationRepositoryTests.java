package com.example.LMS.repository;

import com.example.LMS.entity.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    private Notification notification1;
    private Notification notification2;
    private Notification notification3;

    @BeforeEach
    void setUp() {
        // Set up sample notifications
        notification1 = new Notification();
        notification1.setRecipientUsername("john_doe");
        notification1.setMessage("You have a new assignment");
        notification1.setRead(false);

        notification2 = new Notification();
        notification2.setRecipientUsername("john_doe");
        notification2.setMessage("Your assignment has been graded");
        notification2.setRead(true);

        notification3 = new Notification();
        notification3.setRecipientUsername("jane_doe");
        notification3.setMessage("Your course has been updated");
        notification3.setRead(false);

        // Save the notifications to the repository
        notificationRepository.save(notification1);
        notificationRepository.save(notification2);
        notificationRepository.save(notification3);
    }

    @Test
    void testFindByRecipientUsernameAndIsRead() {
        // Fetch notifications for user 'john_doe' that are unread
        List<Notification> unreadNotifications = notificationRepository.findByRecipientUsernameAndIsRead("john_doe", false);

        // Verify that the correct notification is retrieved
        assertNotNull(unreadNotifications);
        assertEquals(1, unreadNotifications.size());
        assertEquals("You have a new assignment", unreadNotifications.get(0).getMessage());
    }

    @Test
    void testFindByRecipientUsername() {
        // Fetch notifications for user 'john_doe'
        List<Notification> johnNotifications = notificationRepository.findByRecipientUsername("john_doe");

        // Verify that two notifications are returned for 'john_doe'
        assertNotNull(johnNotifications);
        assertEquals(2, johnNotifications.size());
        assertTrue(johnNotifications.stream().anyMatch(n -> n.getMessage().equals("You have a new assignment")));
        assertTrue(johnNotifications.stream().anyMatch(n -> n.getMessage().equals("Your assignment has been graded")));
    }

    @Test
    void testFindNoNotificationsForUnknownUser() {
        // Fetch notifications for a user with no notifications
        List<Notification> unknownUserNotifications = notificationRepository.findByRecipientUsername("unknown_user");

        // Verify that no notifications are returned
        assertNotNull(unknownUserNotifications);
        assertTrue(unknownUserNotifications.isEmpty());
    }
}
