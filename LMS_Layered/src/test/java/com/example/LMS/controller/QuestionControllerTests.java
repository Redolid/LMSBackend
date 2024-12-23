package com.example.LMS.controller;

import com.example.LMS.entity.Question;
import com.example.LMS.entity.Quiz;
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

class QuestionControllerTests {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionController questionController;

    private Question question1;
    private Question question2;
    private Question newQuestion;
    private Question updatedQuestion;
    private Quiz quiz;
    private List<Question> mockQuestions;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        quiz = new Quiz();
        quiz.setId(1L);
        quiz.setTitle("Sample Quiz");

        question1 = new Question();
        question1.setId(1L);
        question1.setQuestionText("Question 1");
        question1.setCorrectAnswer("Option A");
        question1.setQuiz(quiz);

        question2 = new Question();
        question2.setId(2L);
        question2.setQuestionText("Question 2");
        question2.setCorrectAnswer("Option B");
        question2.setQuiz(quiz);

        newQuestion = new Question();
        newQuestion.setQuestionText("New Question");
        newQuestion.setCorrectAnswer("Option A");
        newQuestion.setQuiz(quiz);

        updatedQuestion = new Question();
        updatedQuestion.setId(1L);
        updatedQuestion.setQuestionText("Updated Question");
        updatedQuestion.setCorrectAnswer("Option B");
        updatedQuestion.setQuiz(quiz);

        mockQuestions = Arrays.asList(question1, question2);
    }

    @Test
    void testTestEndpoint() {
        String response = questionController.testEndpoint();
        assertEquals("Hello, From Question Controller!", response);
    }

    @Test
    void testGetQuestionsByQuiz() {
        when(questionService.getQuestionsByQuiz(quiz.getId())).thenReturn(mockQuestions);

        ResponseEntity<List<Question>> response = questionController.getQuestionsByQuiz(quiz.getId());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(questionService, times(1)).getQuestionsByQuiz(quiz.getId());
    }

    @Test
    void testAddQuestion() {
        Question savedQuestion = new Question();
        savedQuestion.setId(1L);
        savedQuestion.setQuestionText(newQuestion.getQuestionText());
        savedQuestion.setCorrectAnswer(newQuestion.getCorrectAnswer());
        savedQuestion.setQuiz(newQuestion.getQuiz());

        when(questionService.addQuestion(newQuestion)).thenReturn(savedQuestion);

        ResponseEntity<Question> response = questionController.addQuestion(newQuestion);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(savedQuestion, response.getBody());
        verify(questionService, times(1)).addQuestion(newQuestion);
    }

    @Test
    void testUpdateQuestion() {
        when(questionService.updateQuestion(1L, updatedQuestion)).thenReturn(updatedQuestion);

        ResponseEntity<Question> response = questionController.updateQuestion(1L, updatedQuestion);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedQuestion, response.getBody());
        verify(questionService, times(1)).updateQuestion(1L, updatedQuestion);
    }

    @Test
    void testDeleteQuestion() {
        doNothing().when(questionService).deleteQuestion(1L);

        ResponseEntity<String> response = questionController.deleteQuestion(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Question deleted successfully!", response.getBody());
        verify(questionService, times(1)).deleteQuestion(1L);
    }
}
