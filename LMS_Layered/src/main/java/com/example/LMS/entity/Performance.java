package com.example.LMS.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private Double quizScore; // Average quiz score
    private int assignmentsSubmitted; // Count of submitted assignments
    private int attendance; // Attendance percentage
    private String overallPerformance; // e.g., Excellent, Good, Needs Improvement
}