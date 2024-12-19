package com.example.LMS.repository;

import com.example.LMS.entity.Course;
import com.example.LMS.dto.CourseRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    private Course course;

    @BeforeEach
    void setUp() {
        // Initialize a sample Course entity
        course = new Course();
        course.setTitle("Introduction to Spring");
        course.setDescription("Learn the basics of Spring Framework.");
        course.setInstructorName("John Doe");
        course.setDurationInHours(40);
    }

    @Test
    void testSaveCourse() {
        // Save the Course entity to the repository
        Course savedCourse = courseRepository.save(course);

        // Verify that the course is saved and has a generated ID
        assertNotNull(savedCourse.getId());
        assertEquals("Introduction to Spring", savedCourse.getTitle());
    }

    @Test
    void testFindCourseById() {
        // Save the Course entity
        Course savedCourse = courseRepository.save(course);

        // Retrieve the saved Course by its ID
        Course foundCourse = courseRepository.findById(savedCourse.getId()).orElse(null);

        // Verify that the course was found and is not null
        assertNotNull(foundCourse);
        assertEquals(savedCourse.getId(), foundCourse.getId());
        assertEquals("Introduction to Spring", foundCourse.getTitle());
    }

    @Test
    void testUpdateCourse() {
        // Save the Course entity
        Course savedCourse = courseRepository.save(course);

        // Update the course
        savedCourse.setTitle("Advanced Spring");
        savedCourse.setDurationInHours(50);
        Course updatedCourse = courseRepository.save(savedCourse);

        // Verify that the course has been updated
        assertNotNull(updatedCourse);
        assertEquals("Advanced Spring", updatedCourse.getTitle());
        assertEquals(50, updatedCourse.getDurationInHours());
    }

    @Test
    void testDeleteCourse() {
        // Save the Course entity
        Course savedCourse = courseRepository.save(course);

        // Delete the saved Course
        courseRepository.delete(savedCourse);

        // Verify that the course no longer exists in the repository
        Course deletedCourse = courseRepository.findById(savedCourse.getId()).orElse(null);
        assertNull(deletedCourse);
    }
}
