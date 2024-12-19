package com.example.LMS.service;

import com.example.LMS.dto.CourseRequestDTO;
import com.example.LMS.entity.Course;
import com.example.LMS.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseServiceTests {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    private Course course;
    private CourseRequestDTO courseRequestDTO;

    @BeforeEach
    void setUp() {
        // Set up the Course and CourseRequestDTO for testing
        course = new Course();
        course.setId(1L);
        course.setTitle("Java Programming");
        course.setDescription("Learn Java from scratch");
        course.setInstructorName("John Doe");
        course.setDurationInHours(40);

        courseRequestDTO = new CourseRequestDTO();
        courseRequestDTO.setTitle("Advanced Java");
        courseRequestDTO.setDescription("Learn advanced Java concepts");
        courseRequestDTO.setInstructorName("Jane Doe");
        courseRequestDTO.setDurationInHours(50);
    }

    @Test
    void testAddCourse() {
        // Mock the behavior of the repository
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // Call the method to add a course
        Course createdCourse = courseService.addCourse(courseRequestDTO, anyLong());

        // Verify that the method worked and the returned course is correct
        assertNotNull(createdCourse);
        assertEquals("Java Programming", createdCourse.getTitle());

        // Verify that the save method was called once
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testGetAllCourses() {
        // Mock the behavior of the repository to return a list of courses
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course));

        // Call the method to get all courses
        List<Course> courses = courseService.getAllCourses();

        // Verify that the courses list is not empty
        assertNotNull(courses);
        assertFalse(courses.isEmpty());

        // Verify that findAll method was called once
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void testGetCourseById() {
        // Mock the behavior of the repository to return the course when searching by ID
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        // Call the method to get the course by ID
        Course foundCourse = courseService.getCourseById(1L);

        // Verify that the course is found and correct
        assertNotNull(foundCourse);
        assertEquals("Java Programming", foundCourse.getTitle());

        // Verify that findById method was called once
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCourseById_NotFound() {
        // Mock the behavior of the repository to return an empty Optional for a non-existent course
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the method to get the course by ID and expect an exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            courseService.getCourseById(1L);
        });

        // Assert that the exception is thrown with the correct message
        assertEquals("Course not found!", exception.getMessage());

        // Verify that findById method was called once
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateCourse() {
        // Mock the behavior of the repository to return the course when searching by ID
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // Call the method to update the course
        Course updatedCourse = courseService.updateCourse(1L, courseRequestDTO);

        // Verify that the course details are updated correctly
        assertEquals("Advanced Java", updatedCourse.getTitle());
        assertEquals("Learn advanced Java concepts", updatedCourse.getDescription());

        // Verify that findById and save methods were called once
        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testDeleteCourse() {
        // Mock the behavior of the repository to find the course
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        // Call the method to delete the course
        courseService.deleteCourse(1L);

        // Verify that the deleteById method was called once
        verify(courseRepository, times(1)).deleteById(1L);
    }
}
