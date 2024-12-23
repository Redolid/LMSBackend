package com.example.LMS.controller;

import com.example.LMS.entity.Question;
import com.example.LMS.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class QuestionControllerTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionController questionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTestEndpoint() {
        String response = questionController.testEndpoint();
        assertEquals("Hello, From Question Controller!", response);
    }

    @Test
    void testGetQuestionsByQuiz() {
        Long quizId = 1L;
        List<Question> mockQuestions = Arrays.asList(
                new Question(1L, "Question 1", "Option A", "Option B", "Option C", "Option D", "Option A", quizId),
                new Question(2L, "Question 2", "Option A", "Option B", "Option C", "Option D", "Option B", quizId)
        );

        when(questionService.getQuestionsByQuiz(quizId)).thenReturn(mockQuestions);

        ResponseEntity<List<Question>> response = questionController.getQuestionsByQuiz(quizId);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(questionService, times(1)).getQuestionsByQuiz(quizId);
    }

    @Test
    void testAddQuestion() {
        Question question = new Question(null, "New Question", "Option A", "Option B", "Option C", "Option D", "Option A", 1L);
        Question savedQuestion = new Question(1L, "New Question", "Option A", "Option B", "Option C", "Option D", "Option A", 1L);

        when(questionService.addQuestion(question)).thenReturn(savedQuestion);

        ResponseEntity<Question> response = questionController.addQuestion(question);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(savedQuestion, response.getBody());
        verify(questionService, times(1)).addQuestion(question);
    }

    @Test
    void testUpdateQuestion() {
        Long questionId = 1L;
        Question updatedQuestion = new Question(questionId, "Updated Question", "Option A", "Option B", "Option C", "Option D", "Option A", 1L);

        when(questionService.updateQuestion(questionId, updatedQuestion)).thenReturn(updatedQuestion);

        ResponseEntity<Question> response = questionController.updateQuestion(questionId, updatedQuestion);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedQuestion, response.getBody());
        verify(questionService, times(1)).updateQuestion(questionId, updatedQuestion);
    }

    @Test
    void testDeleteQuestion() {
        Long questionId = 1L;

        doNothing().when(questionService).deleteQuestion(questionId);

        ResponseEntity<String> response = questionController.deleteQuestion(questionId);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Question deleted successfully!", response.getBody());
        verify(questionService, times(1)).deleteQuestion(questionId);
    }
}
