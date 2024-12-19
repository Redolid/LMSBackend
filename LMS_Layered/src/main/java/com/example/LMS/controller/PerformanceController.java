package com.example.LMS.controller;

import com.example.LMS.entity.Performance;
import com.example.LMS.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performance")
public class PerformanceController {

    @Autowired
    private PerformanceService performanceService;

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Performance>> getPerformanceByCourse(@PathVariable Long courseId) {
        List<Performance> performanceList = performanceService.getPerformanceByCourse(courseId);
        return ResponseEntity.ok(performanceList);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Performance>> getPerformanceByStudent(@PathVariable Long studentId) {
        List<Performance> performanceList = performanceService.getPerformanceByStudent(studentId);
        return ResponseEntity.ok(performanceList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Performance> updatePerformance(
            @PathVariable Long id,
            @RequestBody Performance performance) {
        performance.setId(id);
        return ResponseEntity.ok(performanceService.updatePerformance(performance));
    }

    @PostMapping("/calculate")
    public ResponseEntity<Void> calculatePerformance(@RequestBody Performance performance) {
        performanceService.calculateOverallPerformance(performance);
        return ResponseEntity.ok().build();
    }
}