package com.example.LMS.repository;

import com.example.LMS.entity.Performance;
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
        Performance performance1 = new Performance(null, 1L, 1L, 85.0, 90, 5, "Good");
        Performance performance2 = new Performance(null, 1L, 2L, 95.0, 95, 6, "Excellent");
        Performance performance3 = new Performance(null, 2L, 1L, 70.0, 75, 4, "Good");

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
        assertEquals(1L, performances.get(0).getCourseId());
        assertEquals(1L, performances.get(1).getCourseId());
    }

    @Test
    void testFindByStudentId() {
        Long studentId = 1L;

        List<Performance> performances = performanceRepository.findByStudentId(studentId);

        assertNotNull(performances);
        assertEquals(2, performances.size());
        assertEquals(1L, performances.get(0).getStudentId());
        assertEquals(1L, performances.get(1).getStudentId());
    }

    @Test
    void testSavePerformance() {
        Performance performance = new Performance(null, 3L, 3L, 80.0, 85, 5, "Good");

        Performance savedPerformance = performanceRepository.save(performance);

        assertNotNull(savedPerformance.getId());
        assertEquals(3L, savedPerformance.getCourseId());
        assertEquals(3L, savedPerformance.getStudentId());
        assertEquals(80.0, savedPerformance.getQuizScore());
        assertEquals("Good", savedPerformance.getOverallPerformance());
    }
}
