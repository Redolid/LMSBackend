package com.example.LMS.service;

import com.example.LMS.entity.Assignment;
import com.example.LMS.repository.AssignmentRepository;
import com.example.LMS.utils.NotificationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AssignmentServiceTests {

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private NotificationUtils notificationUtils;

    @InjectMocks
    private AssignmentService assignmentService;

    private Assignment assignment;

    @BeforeEach
    void setUp() {
        // Set up test data
        assignment = new Assignment();
        assignment.setId(1L);
        assignment.setTitle("Math Assignment");
        assignment.setDescription("Complete all exercises.");
        
        // Assuming assignment has a Student and Course
        // These should be set up based on your domain model
    }

    @Test
    void testSubmitAssignment() {
        // Mock the behavior of the repository and notification utility
        when(assignmentRepository.save(assignment)).thenReturn(assignment);

        // Mock the behavior of notificationUtil
        doNothing().when(notificationUtils).sendNotificationToInstructors(anyString(), anyLong());

        // Call the method to test
        Assignment submittedAssignment = assignmentService.submitAssignment(assignment);

        // Assert that the returned assignment is the same as the one passed in
        assertNotNull(submittedAssignment);
        assertEquals("Math Assignment", submittedAssignment.getTitle());

        // Verify the interactions
        verify(assignmentRepository, times(1)).save(assignment);
        verify(notificationUtils, times(1)).sendNotificationToInstructors(anyString(), anyLong());
    }

    @Test
    void testGradeAssignment() {
        // Set up mock data for the graded assignment
        when(assignmentRepository.findById(1L)).thenReturn(java.util.Optional.of(assignment));
        when(assignmentRepository.save(assignment)).thenReturn(assignment);

        // Mock the behavior of notificationUtil
        doNothing().when(notificationUtils).sendNotificationToUser(anyString(), anyLong());

        // Call the method to grade the assignment
        Assignment gradedAssignment = assignmentService.gradeAssignment(1L, "A", "Well done!");

        // Assert the grade and feedback were set correctly
        assertEquals("A", gradedAssignment.getGrade());
        assertEquals("Well done!", gradedAssignment.getFeedback());

        // Verify the interactions
        verify(assignmentRepository, times(1)).findById(1L);
        verify(assignmentRepository, times(1)).save(assignment);
        verify(notificationUtils, times(1)).sendNotificationToUser(anyString(), anyLong());
    }

    @Test
    void testGradeAssignmentNotFound() {
        // Mock the behavior for when the assignment is not found
        when(assignmentRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Call the method and expect an exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            assignmentService.gradeAssignment(1L, "A", "Well done!");
        });

        // Assert that the exception message is as expected
        assertEquals("Assignment not found", exception.getMessage());

        // Verify that no save or notification happened
        verify(assignmentRepository, times(1)).findById(1L);
        verify(assignmentRepository, times(0)).save(any(Assignment.class));
        verify(notificationUtils, times(0)).sendNotificationToUser(anyString(), anyLong());
    }
}
