package com.example.LMS.service;

import com.example.LMS.entity.Question;
import com.example.LMS.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionServiceTests {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetQuestionsByQuiz() {
        Long quizId = 1L;
        List<Question> mockQuestions = Arrays.asList(
                new Question(1L, "Question 1", "MCQ", "Option A", 1L),
                new Question(2L, "Question 2", "MCQ", "Option B", 1L)
        );

        when(questionRepository.findAllByQuizId(quizId)).thenReturn(mockQuestions);

        List<Question> result = questionService.getQuestionsByQuiz(quizId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(questionRepository, times(1)).findAllByQuizId(quizId);
    }

    @Test
    void testAddQuestion() {
        Question newQuestion = new Question(null, "New Question", "MCQ", "Option A", 1L);
        Question savedQuestion = new Question(1L, "New Question", "MCQ", "Option A", 1L);

        when(questionRepository.save(newQuestion)).thenReturn(savedQuestion);

        Question result = questionService.addQuestion(newQuestion);

        assertNotNull(result);
        assertEquals(savedQuestion.getId(), result.getId());
        verify(questionRepository, times(1)).save(newQuestion);
    }

    @Test
    void testUpdateQuestion() {
        Long questionId = 1L;
        Question existingQuestion = new Question(questionId, "Old Question", "MCQ", "Option A", 1L);
        Question updatedQuestion = new Question(questionId, "Updated Question", "MCQ", "Option B", 1L);

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(existingQuestion));
        when(questionRepository.save(existingQuestion)).thenReturn(updatedQuestion);

        Question result = questionService.updateQuestion(questionId, updatedQuestion);

        assertNotNull(result);
        assertEquals("Updated Question", result.getQuestionText());
        assertEquals("Option B", result.getCorrectAnswer());
        verify(questionRepository, times(1)).findById(questionId);
        verify(questionRepository, times(1)).save(existingQuestion);
    }

    @Test
    void testUpdateQuestionNotFound() {
        Long questionId = 1L;
        Question updatedQuestion = new Question(questionId, "Updated Question", "MCQ", "Option B", 1L);

        when(questionRepository.findById(questionId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            questionService.updateQuestion(questionId, updatedQuestion);
        });

        assertEquals("Question not found", exception.getMessage());
        verify(questionRepository, times(1)).findById(questionId);
        verify(questionRepository, times(0)).save(any());
    }

    @Test
    void testDeleteQuestion() {
        Long questionId = 1L;

        doNothing().when(questionRepository).deleteById(questionId);

        questionService.deleteQuestion(questionId);

        verify(questionRepository, times(1)).deleteById(questionId);
    }
}
