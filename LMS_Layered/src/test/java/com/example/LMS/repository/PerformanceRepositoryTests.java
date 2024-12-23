package com.example.LMS.repository;

import com.example.LMS.entity.Performance;
import com.example.LMS.entity.User;
import com.example.LMS.entity.Course;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PerformanceRepositoryTests {

    @Autowired
    private PerformanceRepository performanceRepository;

    @BeforeEach
    void setUp() {
        // Insert sample data for testing
    	// Create Student and Course objects
    	User student1 = new User();
    	student1.setId(1L);
    	student1.setUsername("Student 1"); // Assuming there's a name field

    	User student2 = new User();
    	student2.setId(2L);
    	student2.setUsername("Student 2");

    	Course course1 = new Course();
    	course1.setId(1L);
    	course1.setTitle("Course 1"); // Assuming there's a name field

    	Course course2 = new Course();
    	course2.setId(2L);
    	course2.setTitle("Course 2");

    	// Create Performance objects and set properties
    	Performance performance1 = new Performance();
    	performance1.setStudent(student1);  // Pass the Student object
    	performance1.setCourse(course1);    // Pass the Course object
    	performance1.setQuizScore(85.0);
    	performance1.setOverallPerformance("Good");

    	Performance performance2 = new Performance();
    	performance2.setStudent(student2);  // Pass the Student object
    	performance2.setCourse(course1);    // Pass the Course object
    	performance2.setQuizScore(95.0);
    	performance2.setOverallPerformance("Excellent");

    	Performance performance3 = new Performance();
    	performance3.setStudent(student1);  // Pass the Student object
    	performance3.setCourse(course2);    // Pass the Course object
    	performance3.setQuizScore(70.0);
    	performance3.setOverallPerformance("Good");



        performanceRepository.save(performance1);
        performanceRepository.save(performance2);
        performanceRepository.save(performance3);
    }

    @Test
    void testFindByCourseId() {
        Long courseId = 1L;

        List<Performance> performances = performanceRepository.findByCourseId(courseId);

        assertNotNull(performances);
        assertEquals(2, performances.size());
        assertEquals(1L, performances.get(0).getCourse());
        assertEquals(1L, performances.get(1).getCourse());
    }

    @Test
    void testFindByStudentId() {
        Long studentId = 1L;

        List<Performance> performances = performanceRepository.findByStudentId(studentId);

        assertNotNull(performances);
        assertEquals(2, performances.size());
        assertEquals(1L, performances.get(0).getStudent());
        assertEquals(1L, performances.get(1).getStudent());
    }

    @Test
    void testSavePerformance() {
    	// Create Student and Course objects
    	User student3 = new User();
    	student3.setId(3L);
    	student3.setUsername("Student 3"); // Assuming there's a name field

    	Course course3 = new Course();
    	course3.setId(3L);
    	course3.setTitle("Course 3"); // Assuming there's a name field

    	// Create a Performance object using setters
    	Performance performance = new Performance();
    	performance.setStudent(student3);    // Set the Student object
    	performance.setCourse(course3);      // Set the Course object
    	performance.setQuizScore(80.0);          // Set the grade
    	performance.setOverallPerformance("Good");      // Set the remarks

        

        Performance savedPerformance = performanceRepository.save(performance);

        assertNotNull(savedPerformance.getId());
        assertEquals(3L, savedPerformance.getCourse());
        assertEquals(3L, savedPerformance.getStudent());
        assertEquals(80.0, savedPerformance.getQuizScore());
        assertEquals("Good", savedPerformance.getOverallPerformance());
    }
}
