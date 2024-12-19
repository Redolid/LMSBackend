package com.example.LMS.service;

import com.example.LMS.entity.Quiz;
import com.example.LMS.entity.Question;
import com.example.LMS.repository.QuizRepository;
import com.example.LMS.repository.QuestionRepository;
import com.example.LMS.utils.NotificationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuizServiceTests {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private NotificationUtils notificationUtils;

    @InjectMocks
    private QuizService quizService;

    private Quiz quiz;
    private Question question;

    @BeforeEach
    void setUp() {
        // Initialize test data
        quiz = new Quiz();
        quiz.setId(1L);
        quiz.setTitle("Java Basics Quiz");

        question = new Question();
        question.setId(1L);
        question.setQuestionText("What is Java?");
        question.setQuiz(quiz);
    }

    @Test
    void testCreateQuiz() {
        // Mock the behavior of the repository to save the quiz
        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);

        // Mock sending a notification
        doNothing().when(notificationUtils).sendNotificationToInstructors(anyString(), anyLong());

        // Call the method to create a quiz
        Quiz createdQuiz = quizService.createQuiz(quiz);

        // Verify that the returned quiz is correct
        assertNotNull(createdQuiz);
        assertEquals("Java Basics Quiz", createdQuiz.getTitle());

        // Verify that the save method was called once
        verify(quizRepository, times(1)).save(any(Quiz.class));

        // Verify that the notification method was called once
        verify(notificationUtils, times(1)).sendNotificationToInstructors(
                "New quiz created: Java Basics Quiz", quiz.getCourse().getId());
    }

    @Test
    void testAddQuestionToQuiz() {
        // Mock the behavior of the repository to find the quiz and save the question
        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
        when(questionRepository.save(any(Question.class))).thenReturn(question);

        // Call the method to add a question to the quiz
        Question addedQuestion = quizService.addQuestionToQuiz(1L, question);

        // Verify that the returned question is correct
        assertNotNull(addedQuestion);
        assertEquals("What is Java?", addedQuestion.getQuestionText());

        // Verify that the save method was called once
        verify(questionRepository, times(1)).save(any(Question.class));

        // Verify that the findById method was called once
        verify(quizRepository, times(1)).findById(1L);
    }

    @Test
    void testAddQuestionToQuiz_QuizNotFound() {
        // Mock the behavior of the repository to return an empty Optional
        when(quizRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the method and expect an exception to be thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            quizService.addQuestionToQuiz(1L, question);
        });

        // Verify that the exception message is correct
        assertEquals("Quiz not found", exception.getMessage());

        // Verify that the findById method was called once
        verify(quizRepository, times(1)).findById(1L);
    }

    @Test
    void testGetQuestionsForQuiz() {
        // Mock the behavior of the repository to return a list of questions
        when(questionRepository.findAllByQuizId(1L)).thenReturn(Arrays.asList(question));

        // Call the method to get all questions for the quiz
        List<Question> questions = quizService.getQuestionsForQuiz(1L);

        // Verify that the questions list is not empty
        assertNotNull(questions);
        assertFalse(questions.isEmpty());

        // Verify that the correct question is in the list
        assertEquals("What is Java?", questions.get(0).getQuestionText());

        // Verify that the findAllByQuizId method was called once
        verify(questionRepository, times(1)).findAllByQuizId(1L);
    }
}
