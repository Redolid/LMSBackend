package com.example.LMS.repository;

import com.example.LMS.entity.Question;
import com.example.LMS.entity.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    private Quiz quiz1;
    private Quiz quiz2;

    private Question question1;
    private Question question2;
    private Question question3;

    @BeforeEach
    void setUp() {
        // Set up quizzes
        quiz1 = new Quiz();
        quiz1.setTitle("Quiz 1");
        quiz1.setCourse(null);  // Assuming course will be set later or is not relevant for this test.
        quiz1 = quizRepository.save(quiz1);

        quiz2 = new Quiz();
        quiz2.setTitle("Quiz 2");
        quiz2.setCourse(null);  // Same assumption as quiz1
        quiz2 = quizRepository.save(quiz2);

        // Set up questions
        question1 = new Question();
        question1.setQuestionText("What is 2 + 2?");
        question1.setQuiz(quiz1);

        question2 = new Question();
        question2.setQuestionText("What is the capital of France?");
        question2.setQuiz(quiz1);

        question3 = new Question();
        question3.setQuestionText("What is the square root of 16?");
        question3.setQuiz(quiz2);

        // Save questions to the repository
        questionRepository.save(question1);
        questionRepository.save(question2);
        questionRepository.save(question3);
    }

    @Test
    void testFindAllByQuizId() {
        // Fetch questions associated with quiz1
        List<Question> quiz1Questions = questionRepository.findAllByQuizId(quiz1.getId());

        // Verify that quiz1 has two questions
        assertNotNull(quiz1Questions);
        assertEquals(2, quiz1Questions.size());
        assertTrue(quiz1Questions.stream().anyMatch(q -> q.getQuestionText().equals("What is 2 + 2?")));
        assertTrue(quiz1Questions.stream().anyMatch(q -> q.getQuestionText().equals("What is the capital of France?")));

        // Fetch questions associated with quiz2
        List<Question> quiz2Questions = questionRepository.findAllByQuizId(quiz2.getId());

        // Verify that quiz2 has one question
        assertNotNull(quiz2Questions);
        assertEquals(1, quiz2Questions.size());
        assertTrue(quiz2Questions.stream().anyMatch(q -> q.getQuestionText().equals("What is the square root of 16?")));
    }

    @Test
    void testFindNoQuestionsForNonExistingQuiz() {
        // Try fetching questions for a non-existing quiz
        List<Question> nonExistingQuizQuestions = questionRepository.findAllByQuizId(999L);  // Assuming 999L is an ID not in the DB

        // Verify that no questions are returned
        assertNotNull(nonExistingQuizQuestions);
        assertTrue(nonExistingQuizQuestions.isEmpty());
    }
}
