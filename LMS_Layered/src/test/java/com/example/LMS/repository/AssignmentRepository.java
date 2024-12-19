package com.example.LMS.repository;

import com.example.LMS.entity.Assignment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AssignmentRepositoryTest {

    @Autowired
    private AssignmentRepository assignmentRepository;

    private Assignment assignment;

    @BeforeEach
    void setUp() {
        // Initialize a sample Assignment entity
        assignment = new Assignment();
        assignment.setTitle("Sample Assignment");
        assignment.setDescription("This is a test assignment.");
        // Set any other properties required for Assignment
    }

    @Test
    void testSaveAssignment() {
        // Save the Assignment entity to the repository
        Assignment savedAssignment = assignmentRepository.save(assignment);

        // Verify that the assignment is saved and has a generated ID
        assertNotNull(savedAssignment.getId());
        assertEquals("Sample Assignment", savedAssignment.getTitle());
    }

    @Test
    void testFindAssignmentById() {
        // Save the Assignment entity
        Assignment savedAssignment = assignmentRepository.save(assignment);

        // Retrieve the saved Assignment by its ID
        Assignment foundAssignment = assignmentRepository.findById(savedAssignment.getId()).orElse(null);

        // Verify that the assignment was found and is not null
        assertNotNull(foundAssignment);
        assertEquals(savedAssignment.getId(), foundAssignment.getId());
        assertEquals("Sample Assignment", foundAssignment.getTitle());
    }

    @Test
    void testDeleteAssignment() {
        // Save the Assignment entity
        Assignment savedAssignment = assignmentRepository.save(assignment);

        // Delete the saved Assignment
        assignmentRepository.delete(savedAssignment);

        // Verify that the assignment no longer exists in the repository
        Assignment deletedAssignment = assignmentRepository.findById(savedAssignment.getId()).orElse(null);
        assertNull(deletedAssignment);
    }
}
