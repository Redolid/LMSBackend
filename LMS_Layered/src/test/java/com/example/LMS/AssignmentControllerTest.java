package com.example.LMS;

import com.example.LMS.controller.AssignmentController;
import com.example.LMS.entity.Assignment;
import com.example.LMS.service.AssignmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AssignmentController.class)
class AssignmentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AssignmentService assignmentService;

    @InjectMocks
    private AssignmentController assignmentController;

    private Assignment assignment;

    @BeforeEach
    void setUp() {
        assignment = new Assignment();
        assignment.setId(1L);
        assignment.setTitle("Math Assignment");
        assignment.setDescription("Solve the problems.");
    }

    @Test
    void testTestEndpoint() throws Exception {
        mockMvc.perform(get("/api/assignments"))
                .andExpect(status().isOk())
                .andExpect(content().string("Testing, From Assignment Component!"));
    }

    @Test
    void testSubmitAssignment() throws Exception {
        when(assignmentService.submitAssignment(any(Assignment.class))).thenReturn(assignment);

        mockMvc.perform(post("/api/assignments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"title\":\"Math Assignment\",\"description\":\"Solve the problems.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Math Assignment"))
                .andExpect(jsonPath("$.description").value("Solve the problems."));

        verify(assignmentService, times(1)).submitAssignment(any(Assignment.class));
    }

    @Test
    void testGradeAssignment() throws Exception {
        when(assignmentService.gradeAssignment(eq(1L), eq("A+"), eq("Excellent work!"))).thenReturn(assignment);

        mockMvc.perform(put("/api/assignments/{assignmentId}/grade", 1L)
                .param("grade", "A+")
                .param("feedback", "Excellent work!"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Math Assignment"))
                .andExpect(jsonPath("$.description").value("Solve the problems."));

        verify(assignmentService, times(1)).gradeAssignment(eq(1L), eq("A+"), eq("Excellent work!"));
    }
}
