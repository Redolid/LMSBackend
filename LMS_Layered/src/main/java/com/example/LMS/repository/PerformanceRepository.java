package com.example.LMS.repository;

import com.example.LMS.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    List<Performance> findByCourseId(Long courseId);
    List<Performance> findByStudentId(Long studentId);
}