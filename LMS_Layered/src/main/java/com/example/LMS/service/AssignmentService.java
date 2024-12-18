package com.example.LMS.service;

import com.example.LMS.entity.Assignment;
import com.example.LMS.repository.AssignmentRepository;
import com.example.LMS.utils.NotificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private NotificationUtils notificationUtil;

    public Assignment submitAssignment(Assignment assignment) {
        Assignment submittedAssignment = assignmentRepository.save(assignment);
        notificationUtil.sendNotificationToInstructors(
                "New assignment submitted by: " + assignment.getStudent().getUsername(),
                assignment.getCourse().getId()
        );
        return submittedAssignment;
    }

    public Assignment gradeAssignment(Long assignmentId, String grade, String feedback) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        assignment.setGrade(grade);
        assignment.setFeedback(feedback);

        Assignment gradedAssignment = assignmentRepository.save(assignment);

        notificationUtil.sendNotificationToUser(
                "Your assignment has been graded. Grade: " + grade + ", Feedback: " + feedback,
                assignment.getStudent().getId()
        );
        return gradedAssignment;
    }
}
