package com.example.LMS.utils;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class NotificationUtils {

 
    public void sendNotificationToUser(String message, Long userId) {
        if (userId == null || userId <= 0) {
            System.err.println("Invalid user ID. Notification not sent.");
            return;
        }

        if (message == null || message.trim().isEmpty()) {
            System.err.println("Notification message cannot be empty.");
            return;
        }

        // Simulate sending the notification
        System.out.println("Notification to User ID " + userId + ": " + message);
    }

   
    public void sendNotificationToInstructors(String message, Long courseId) {
        if (courseId == null || courseId <= 0) {
            System.err.println("Invalid course ID. Notification not sent.");
            return;
        }

        if (message == null || message.trim().isEmpty()) {
            System.err.println("Notification message cannot be empty.");
            return;
        }

        // Simulate retrieving instructors for the course
        List<Long> instructorIds = getInstructorsByCourse(courseId);

        if (instructorIds.isEmpty()) {
            System.err.println("No instructors found for Course ID " + courseId);
            return;
        }

        // Simulate sending notifications to each instructor
        for (Long instructorId : instructorIds) {
            System.out.println("Notification to Instructor ID " + instructorId + ": " + message);
        }
    }

   
    private List<Long> getInstructorsByCourse(Long courseId) {
        // Simulate instructor retrieval logic (e.g., from a database)
        // For demo purposes, return a hardcoded list
        return List.of(101L, 102L, 103L); // Example instructor IDs
    }
}
