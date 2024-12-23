package com.example.LMS.service;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPerformanceByCourse() {
        Long courseId = 1L;
        List<Performance> mockPerformances = Arrays.asList(
                new Performance(1L, courseId, 1L, 80.0, 90, 5, "Good"),
                new Performance(2L, courseId, 2L, 95.0, 95, 6, "Excellent")
        );

        when(performanceRepository.findByCourseId(courseId)).thenReturn(mockPerformances);

        List<Performance> result = performanceService.getPerformanceByCourse(courseId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(performanceRepository, times(1)).findByCourseId(courseId);
    }

    @Test
    void testGetPerformanceByStudent() {
        Long studentId = 1L;
        List<Performance> mockPerformances = Arrays.asList(
                new Performance(1L, 1L, studentId, 85.0, 90, 5, "Excellent")
        );

        when(performanceRepository.findByStudentId(studentId)).thenReturn(mockPerformances);

        List<Performance> result = performanceService.getPerformanceByStudent(studentId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(performanceRepository, times(1)).findByStudentId(studentId);
    }

    @Test
    void testUpdatePerformance() {
        Performance performance = new Performance(1L, 1L, 1L, 88.0, 92, 6, "Good");
        Performance updatedPerformance = new Performance(1L, 1L, 1L, 90.0, 95, 7, "Excellent");

        when(performanceRepository.save(performance)).thenReturn(updatedPerformance);

        Performance result = performanceService.updatePerformance(performance);

        assertNotNull(result);
        assertEquals("Excellent", result.getOverallPerformance());
        verify(performanceRepository, times(1)).save(performance);
        verify(notificationUtils, times(1)).sendNotificationToUser(
                "Your performance has been updated for course: " + performance.getCourse().getTitle(),
                performance.getStudent().getId()
        );
    }

    @Test
    void testCalculateOverallPerformance_Excellent() {
        Performance performance = new Performance(1L, 1L, 1L, 90.0, 95, 6, "Good");

        performanceService.calculateOverallPerformance(performance);

        assertEquals("Excellent", performance.getOverallPerformance());
        verify(performanceRepository, times(1)).save(performance);
    }

    @Test
    void testCalculateOverallPerformance_Good() {
        Performance performance = new Performance(1L, 1L, 1L, 75.0, 80, 4, "Needs Improvement");

        performanceService.calculateOverallPerformance(performance);

        assertEquals("Good", performance.getOverallPerformance());
        verify(performanceRepository, times(1)).save(performance);
    }

    @Test
    void testCalculateOverallPerformance_NeedsImprovement() {
        Performance performance = new Performance(1L, 1L, 1L, 60.0, 70, 2, "Good");

        performanceService.calculateOverallPerformance(performance);

        assertEquals("Needs Improvement", performance.getOverallPerformance());
        verify(performanceRepository, times(1)).save(performance);
    }
}

