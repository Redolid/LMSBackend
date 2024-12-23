package com.example.LMS.controller;

import com.example.LMS.entity.Performance;
import com.example.LMS.service.PerformanceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PerformanceController.class)
class PerformanceControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PerformanceService performanceService;

    @Test
    void testTestEndpoint() throws Exception {
        mockMvc.perform(get("/api/performance"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, From Performance Controller!"));
    }

    @Test
    void testGetPerformanceByCourse() throws Exception {
        // Mocking the service response
        Performance performance = new Performance();
        performance.setId(1L);
        performance.setQuizScore(95.0);
        List<Performance> performanceList = Collections.singletonList(performance);

        Mockito.when(performanceService.getPerformanceByCourse(1L)).thenReturn(performanceList);

        mockMvc.perform(get("/api/performance/course/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].score").value(95.0));
    }

    @Test
    void testGetPerformanceByStudent() throws Exception {
        // Mocking the service response
        Performance performance = new Performance();
        performance.setId(2L);
        performance.setQuizScore(85.0);
        List<Performance> performanceList = Collections.singletonList(performance);

        Mockito.when(performanceService.getPerformanceByStudent(1L)).thenReturn(performanceList);

        mockMvc.perform(get("/api/performance/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2L))
                .andExpect(jsonPath("$[0].score").value(85.0));
    }

    @Test
    void testUpdatePerformance() throws Exception {
        // Mocking the service response
        Performance performance = new Performance();
        performance.setId(1L);
        performance.setQuizScore(90.0);

        Mockito.when(performanceService.updatePerformance(any(Performance.class))).thenReturn(performance);

        mockMvc.perform(put("/api/performance/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"score\": 90.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.score").value(90.0));
    }

    @Test
    void testCalculatePerformance() throws Exception {
        // No return value from the service method, just ensuring it executes successfully
        Mockito.doNothing().when(performanceService).calculateOverallPerformance(any(Performance.class));

        mockMvc.perform(post("/api/performance/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"score\": 95.0}"))
                .andExpect(status().isOk());
    }
}
