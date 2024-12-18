package com.example.LMS.service;


import com.example.LMS.dto.CourseRequestDTO;
import com.example.LMS.entity.Course;
import com.example.LMS.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService { 

    @Autowired
    private CourseRepository courseRepository;

    public Course addCourse(CourseRequestDTO courseRequestDTO) {
        Course course = new Course();
        course.setTitle(courseRequestDTO.getTitle());
        course.setDescription(courseRequestDTO.getDescription());
        course.setInstructorName(courseRequestDTO.getInstructorName());
        course.setDurationInHours(courseRequestDTO.getDurationInHours());

        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found!"));
    }

    public Course updateCourse(Long id, CourseRequestDTO courseRequestDTO) {
        Course existingCourse = getCourseById(id);
        existingCourse.setTitle(courseRequestDTO.getTitle());
        existingCourse.setDescription(courseRequestDTO.getDescription());
        existingCourse.setInstructorName(courseRequestDTO.getInstructorName());
        existingCourse.setDurationInHours(courseRequestDTO.getDurationInHours());

        return courseRepository.save(existingCourse);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
