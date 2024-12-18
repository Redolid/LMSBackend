package com.example.LMS.controller;

import com.example.LMS.dto.CourseRequestDTO;
import com.example.LMS.entity.Course;
import com.example.LMS.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;


    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody CourseRequestDTO courseRequestDTO) {
        Course createdCourse = courseService.addCourse(courseRequestDTO);
        return ResponseEntity.ok(createdCourse);
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody CourseRequestDTO courseRequestDTO) {
        Course updatedCourse = courseService.updateCourse(id, courseRequestDTO);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Course deleted successfully!");
    }
}
