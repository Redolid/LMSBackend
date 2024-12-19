package com.example.LMS.controller;

import com.example.LMS.entity.Assignment;
import com.example.LMS.entity.Course;
import com.example.LMS.service.AssignmentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {
    @Autowired
    private AssignmentService assignmentService;
    
    @GetMapping
    public String testEndpoint() {
        return "Hello, From Assignment Controller!";
    }

    @PostMapping
    public ResponseEntity<Assignment> submitAssignment(@RequestBody Assignment assignment) {
        return ResponseEntity.ok(assignmentService.submitAssignment(assignment));
    }

    @PutMapping("/{assignmentId}/grade")
    public ResponseEntity<Assignment> gradeAssignment(
            @PathVariable Long assignmentId,
            @RequestParam String grade,
            @RequestParam String feedback
    ) {
        return ResponseEntity.ok(assignmentService.gradeAssignment(assignmentId, grade, feedback));
    }
}
