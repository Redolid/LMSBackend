package com.example.LMS.service;

import com.example.LMS.entity.Question;
import com.example.LMS.entity.Quiz;
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

    private Question question1;
    private Question question2;
    private Question newQuestion;
    private Question updatedQuestion;
    private Quiz quiz;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize shared objects
        quiz = new Quiz();
        quiz.setId(1L);
        quiz.setTitle("Sample Quiz");

        question1 = new Question();
        question1.setId(1L);
        question1.setQuestionText("Question 1");
        question1.setQuestionType("MCQ");
        question1.setCorrectAnswer("Option A");
        question1.setQuiz(quiz);

        question2 = new Question();
        question2.setId(2L);
        question2.setQuestionText("Question 2");
        question2.setQuestionType("MCQ");
        question2.setCorrectAnswer("Option B");
        question2.setQuiz(quiz);

        newQuestion = new Question();
        newQuestion.setQuestionText("New Question");
        newQuestion.setQuestionType("MCQ");
        newQuestion.setCorrectAnswer("Option A");
        newQuestion.setQuiz(quiz);

        updatedQuestion = new Question();
        updatedQuestion.setId(1L);
        updatedQuestion.setQuestionText("Updated Question");
        updatedQuestion.setQuestionType("MCQ");
        updatedQuestion.setCorrectAnswer("Option B");
        updatedQuestion.setQuiz(quiz);
    }

    @Test
    void testGetQuestionsByQuiz() {
        List<Question> mockQuestions = Arrays.asList(question1, question2);

        when(questionRepository.findAllByQuizId(quiz.getId())).thenReturn(mockQuestions);

        List<Question> result = questionService.getQuestionsByQuiz(quiz.getId());

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(questionRepository, times(1)).findAllByQuizId(quiz.getId());
    }

    @Test
    void testAddQuestion() {
        Question savedQuestion = new Question();
        savedQuestion.setId(1L);
        savedQuestion.setQuestionText(newQuestion.getQuestionText());
        savedQuestion.setQuestionType(newQuestion.getQuestionType());
        savedQuestion.setCorrectAnswer(newQuestion.getCorrectAnswer());
        savedQuestion.setQuiz(newQuestion.getQuiz());

        when(questionRepository.save(newQuestion)).thenReturn(savedQuestion);

        Question result = questionService.addQuestion(newQuestion);

        assertNotNull(result);
        assertEquals(savedQuestion.getId(), result.getId());
        assertEquals(savedQuestion.getQuiz(), result.getQuiz());
        verify(questionRepository, times(1)).save(newQuestion);
    }

    @Test
    void testUpdateQuestion() {
        Long questionId = 1L;

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question1));
        when(questionRepository.save(question1)).thenReturn(updatedQuestion);

        Question result = questionService.updateQuestion(questionId, updatedQuestion);

        assertNotNull(result);
        assertEquals("Updated Question", result.getQuestionText());
        assertEquals("Option B", result.getCorrectAnswer());
        assertEquals(quiz, result.getQuiz());
        verify(questionRepository, times(1)).findById(questionId);
        verify(questionRepository, times(1)).save(question1);
    }

    @Test
    void testUpdateQuestionNotFound() {
        Long questionId = 1L;

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
