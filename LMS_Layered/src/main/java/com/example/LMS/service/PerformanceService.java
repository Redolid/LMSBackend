package com.example.LMS.service;

import com.example.LMS.entity.Performance;
import com.example.LMS.repository.PerformanceRepository;
import com.example.LMS.utils.NotificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceService {

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private NotificationUtils notificationUtil;

    public List<Performance> getPerformanceByCourse(Long courseId) {
        return performanceRepository.findByCourseId(courseId);
    }

    public List<Performance> getPerformanceByStudent(Long studentId) {
        return performanceRepository.findByStudentId(studentId);
    }

    public Performance updatePerformance(Performance performance) {
        Performance updatedPerformance = performanceRepository.save(performance);
        notificationUtil.sendNotificationToUser(
                "Your performance has been updated for course: " + performance.getCourse().getTitle(),
                performance.getStudent().getId()
        );
        return updatedPerformance;
    }

    public void calculateOverallPerformance(Performance performance) {
        double avgScore = performance.getQuizScore();
        int attendance = performance.getAttendance();
        int assignmentsSubmitted = performance.getAssignmentsSubmitted();

        if (avgScore >= 85 && attendance >= 90 && assignmentsSubmitted >= 5) {
            performance.setOverallPerformance("Excellent");
        } else if (avgScore >= 70 && attendance >= 75) {
            performance.setOverallPerformance("Good");
        } else {
            performance.setOverallPerformance("Needs Improvement");
        }

        performanceRepository.save(performance);
    }
}