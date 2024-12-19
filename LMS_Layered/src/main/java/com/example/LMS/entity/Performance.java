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
    
 // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Double getQuizScore() {
        return quizScore;
    }

    public void setQuizScore(Double quizScore) {
        this.quizScore = quizScore;
    }

    public int getAssignmentsSubmitted() {
        return assignmentsSubmitted;
    }

    public void setAssignmentsSubmitted(int assignmentsSubmitted) {
        this.assignmentsSubmitted = assignmentsSubmitted;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public String getOverallPerformance() {
        return overallPerformance;
    }

    public void setOverallPerformance(String overallPerformance) {
        this.overallPerformance = overallPerformance;
    }
}