package com.example.LMS.controller;

import com.example.LMS.dto.CourseRequestDTO;
import com.example.LMS.entity.Course;
import com.example.LMS.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
class CourseControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    private Course course;
    private CourseRequestDTO courseRequestDTO;

    @BeforeEach
    void setUp() {
        // Setting up test data
        course = new Course();
        course.setId(1L);
        course.setTitle("Mathematics 101");
        course.setDescription("Introductory math course");

        courseRequestDTO = new CourseRequestDTO();
        courseRequestDTO.setTitle("Mathematics 101");
        courseRequestDTO.setDescription("Introductory math course");
    }

    @Test
    void testAddCourse() throws Exception {
        when(courseService.addCourse(any(CourseRequestDTO.class), anyLong())).thenReturn(course);

        mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Mathematics 101\",\"description\":\"Introductory math course\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Mathematics 101"))
                .andExpect(jsonPath("$.description").value("Introductory math course"));

        verify(courseService, times(1)).addCourse(any(CourseRequestDTO.class), anyLong());
    }

    @Test
    void testGetAllCourses() throws Exception {
        List<Course> courses = Arrays.asList(course);
        when(courseService.getAllCourses()).thenReturn(courses);

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Mathematics 101"))
                .andExpect(jsonPath("$[0].description").value("Introductory math course"));

        verify(courseService, times(1)).getAllCourses();
    }

    @Test
    void testGetCourseById() throws Exception {
        when(courseService.getCourseById(1L)).thenReturn(course);

        mockMvc.perform(get("/api/courses/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Mathematics 101"))
                .andExpect(jsonPath("$.description").value("Introductory math course"));

        verify(courseService, times(1)).getCourseById(1L);
    }

    @Test
    void testUpdateCourse() throws Exception {
        when(courseService.updateCourse(eq(1L), any(CourseRequestDTO.class))).thenReturn(course);

        mockMvc.perform(put("/api/courses/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Mathematics 101 Updated\",\"description\":\"Updated course description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Mathematics 101"))
                .andExpect(jsonPath("$.description").value("Introductory math course"));

        verify(courseService, times(1)).updateCourse(eq(1L), any(CourseRequestDTO.class));
    }

    @Test
    void testDeleteCourse() throws Exception {
        doNothing().when(courseService).deleteCourse(1L);

        mockMvc.perform(delete("/api/courses/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Course deleted successfully!"));

        verify(courseService, times(1)).deleteCourse(1L);
    }
}
