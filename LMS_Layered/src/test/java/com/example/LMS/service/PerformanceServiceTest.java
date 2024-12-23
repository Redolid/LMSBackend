package com.example.LMS.service;

import com.example.LMS.entity.User;
import com.example.LMS.entity.Course;
import com.example.LMS.entity.Performance;
import com.example.LMS.repository.PerformanceRepository;
import com.example.LMS.utils.NotificationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PerformanceServiceTests {

    @Mock
    private PerformanceRepository performanceRepository;

    @Mock
    private NotificationUtils notificationUtils;

    @InjectMocks
    private PerformanceService performanceService;

    private Course course;
    private User student1;
    private User student2;
    private Performance performance1;
    private Performance performance2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize reusable objects
        course = new Course();
        course.setId(1L);
        course.setTitle("Course 1");

        student1 = new User();
        student1.setId(1L);
        student1.setUsername("Student 1");

        student2 = new User();
        student2.setId(2L);
        student2.setUsername("Student 2");

        performance1 = new Performance();
        performance1.setId(1L);
        performance1.setCourse(course);
        performance1.setStudent(student1);
        performance1.setQuizScore(80.0);
        performance1.setOverallPerformance("Good");

        performance2 = new Performance();
        performance2.setId(2L);
        performance2.setCourse(course);
        performance2.setStudent(student2);
        performance2.setQuizScore(95.0);
        performance2.setOverallPerformance("Excellent");
    }

    @Test
    void testGetPerformanceByCourse() {
        when(performanceRepository.findByCourseId(course.getId()))
                .thenReturn(Arrays.asList(performance1, performance2));

        List<Performance> result = performanceService.getPerformanceByCourse(course.getId());

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(performanceRepository, times(1)).findByCourseId(course.getId());
    }

    @Test
    void testGetPerformanceByStudent() {
        when(performanceRepository.findByStudentId(student1.getId()))
                .thenReturn(Arrays.asList(performance1));

        List<Performance> result = performanceService.getPerformanceByStudent(student1.getId());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Good", result.get(0).getOverallPerformance());
        verify(performanceRepository, times(1)).findByStudentId(student1.getId());
    }

    @Test
    void testUpdatePerformance() {
        performance1.setOverallPerformance("Needs Improvement");

        when(performanceRepository.save(performance1)).thenReturn(performance1);

        Performance result = performanceService.updatePerformance(performance1);

        assertNotNull(result);
        assertEquals("Needs Improvement", result.getOverallPerformance());
        verify(performanceRepository, times(1)).save(performance1);
        verify(notificationUtils, times(1)).sendNotificationToUser(
                "Your performance has been updated for course: " + course.getTitle(),
                student1.getId()
        );
    }

    @Test
    void testCalculateOverallPerformance_Excellent() {
        performance1.setQuizScore(90.0);

        performanceService.calculateOverallPerformance(performance1);

        assertEquals("Excellent", performance1.getOverallPerformance());
        verify(performanceRepository, times(1)).save(performance1);
    }
    
    @Test
    void testCalculateOverallPerformance_Good() {
    	performance1.setQuizScore(75.0);

        performanceService.calculateOverallPerformance(performance1);

        assertEquals("Good", performance1.getOverallPerformance());
        verify(performanceRepository, times(1)).save(performance1);
    }

    @Test
    void testCalculateOverallPerformance_NeedsImprovement() {
        performance1.setQuizScore(50.0);

        performanceService.calculateOverallPerformance(performance1);

        assertEquals("Needs Improvement", performance1.getOverallPerformance());
        verify(performanceRepository, times(1)).save(performance1);
    }
}


