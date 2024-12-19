package com.example.LMS.controller;

import com.example.LMS.dto.CourseRequestDTO;
import com.example.LMS.dto.LessonDTO;
import com.example.LMS.entity.Course;
import com.example.LMS.entity.Lesson;
import com.example.LMS.entity.User;
import com.example.LMS.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Set;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // --- Feature 1: Course Management ---

    @PostMapping("/create")
    public ResponseEntity<Course> createCourse(
            @RequestBody CourseRequestDTO courseRequestDTO,
            @RequestParam Long instructorId) {
        Course course = courseService.addCourse(courseRequestDTO, instructorId);
        return ResponseEntity.ok(course);
    }

    @PostMapping("/{courseId}/media")
    public ResponseEntity<Course> uploadMedia(
            @PathVariable Long courseId,
            @RequestParam("file") MultipartFile mediaFile) {
        Course course = courseService.addMediaToCourse(courseId, mediaFile);
        return ResponseEntity.ok(course);
    }

    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<Lesson> addLesson(
            @PathVariable Long courseId,
            @RequestBody LessonDTO lessonDTO) {
        Lesson lesson = courseService.addLesson(courseId, lessonDTO);
        return ResponseEntity.ok(lesson);
    }

    // --- Feature 2: Enrollment Management ---

    @GetMapping("/available")
    public ResponseEntity<List<Course>> viewAvailableCourses() {
        List<Course> courses = courseService.viewAvailableCourses();
        return ResponseEntity.ok(courses);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @PostMapping("/{courseId}/enroll")
    public ResponseEntity<String> enrollStudent(
            @PathVariable Long courseId,
            @RequestParam Long studentId) {
        courseService.enrollStudent(courseId, studentId);
        return ResponseEntity.ok("Student enrolled successfully!");
    }

    @GetMapping("/{courseId}/students")
    public ResponseEntity<Set<User>> viewEnrolledStudents(
            @PathVariable Long courseId,
            @RequestParam Long userId) {
    	Set<User> students = courseService.viewEnrolledStudents(courseId, userId);
        return ResponseEntity.ok(students);
    }

    // --- Feature 3: Attendance Management ---

    @PostMapping("/{lessonId}/generate-otp")
    public ResponseEntity<String> generateOtp(
            @PathVariable Long lessonId,
            @RequestParam Long instructorId) {
        String otp = courseService.generateLessonOtp(lessonId, instructorId);
        return ResponseEntity.ok("OTP generated: " + otp);
    }

    @PostMapping("/{lessonId}/attendance")
    public ResponseEntity<String> markAttendance(
            @PathVariable Long lessonId,
            @RequestParam Long studentId,
            @RequestParam String otp) {
        courseService.markAttendance(lessonId, studentId, otp);
        return ResponseEntity.ok("Attendance marked successfully!");
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
