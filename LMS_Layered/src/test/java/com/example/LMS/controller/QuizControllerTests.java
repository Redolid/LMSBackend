package com.example.LMS.controller;

import com.example.LMS.entity.Quiz;
import com.example.LMS.entity.Question;
import com.example.LMS.service.QuizService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuizController.class)
class QuizControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private QuizService quizService;

    @InjectMocks
    private QuizController quizController;

    private Quiz quiz;
    private Question question;

    @BeforeEach
    void setUp() {
        // Set up test data
        quiz = new Quiz();
        quiz.setId(1L);
        quiz.setName("Test Quiz");

        question = new Question();
        question.setId(1L);
        question.setText("What is 2 + 2?");
        question.setAnswer("4");
    }

    @Test
    void testTestEndpoint() throws Exception {
        mockMvc.perform(get("/api/quizzes"))
                .andExpect(status().isOk())
                .andExpect(content().string("Testing, From Quiz Component!"));
    }

    @Test
    void testCreateQuiz() throws Exception {
        when(quizService.createQuiz(any(Quiz.class))).thenReturn(quiz);

        mockMvc.perform(post("/api/quizzes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test Quiz\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Quiz"));

        verify(quizService, times(1)).createQuiz(any(Quiz.class));
    }

    @Test
    void testAddQuestionToQuiz() throws Exception {
        when(quizService.addQuestionToQuiz(anyLong(), any(Question.class))).thenReturn(question);

        mockMvc.perform(post("/api/quizzes/{quizId}/questions", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"text\":\"What is 2 + 2?\",\"answer\":\"4\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("What is 2 + 2?"))
                .andExpect(jsonPath("$.answer").value("4"));

        verify(quizService, times(1)).addQuestionToQuiz(eq(1L), any(Question.class));
    }

    @Test
    void testGetQuestionsForQuiz() throws Exception {
        List<Question> questions = Arrays.asList(question);
        when(quizService.getQuestionsForQuiz(1L)).thenReturn(questions);

        mockMvc.perform(get("/api/quizzes/{quizId}/questions", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].text").value("What is 2 + 2?"))
                .andExpect(jsonPath("$[0].answer").value("4"));

        verify(quizService, times(1)).getQuestionsForQuiz(1L);
    }
}
