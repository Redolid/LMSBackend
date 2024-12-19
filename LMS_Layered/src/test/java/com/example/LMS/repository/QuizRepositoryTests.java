package com.example.LMS.repository;

import com.example.LMS.entity.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class QuizRepositoryTest {

    @Autowired
    private QuizRepository quizRepository;

    private Quiz quiz1;
    private Quiz quiz2;

    @BeforeEach
    void setUp() {
        // Set up quizzes
        quiz1 = new Quiz();
        quiz1.setTitle("Math Quiz");
        quiz1.setCourse(null);  // Assuming course will be set later or is not relevant for this test.
        
        quiz2 = new Quiz();
        quiz2.setTitle("Science Quiz");
        quiz2.setCourse(null);  // Same assumption as quiz1
        
        // Save quizzes to the repository
        quizRepository.save(quiz1);
        quizRepository.save(quiz2);
    }

    @Test
    void testSaveQuiz() {
        // Save a new quiz and check if it is correctly saved
        Quiz newQuiz = new Quiz();
        newQuiz.setTitle("History Quiz");
        newQuiz.setCourse(null); // Assuming course is not important for this test
        
        Quiz savedQuiz = quizRepository.save(newQuiz);

        assertNotNull(savedQuiz);
        assertNotNull(savedQuiz.getId());
        assertEquals("History Quiz", savedQuiz.getTitle());
    }

    @Test
    void testFindById() {
        // Find quiz by ID
        Optional<Quiz> foundQuiz = quizRepository.findById(quiz1.getId());

        assertTrue(foundQuiz.isPresent());
        assertEquals("Math Quiz", foundQuiz.get().getTitle());
    }

    @Test
    void testFindByIdNotFound() {
        // Try finding a quiz that doesn't exist
        Optional<Quiz> foundQuiz = quizRepository.findById(999L);  // Using an ID that doesn't exist

        assertFalse(foundQuiz.isPresent());
    }

    @Test
    void testFindAll() {
        // Find all quizzes and ensure they exist
        var quizzes = quizRepository.findAll();
        assertNotNull(quizzes);
        assertEquals(2, quizzes.size());  // We saved 2 quizzes in setUp()
    }

    @Test
    void testDeleteById() {
        // Delete a quiz by ID
        quizRepository.deleteById(quiz1.getId());

        // Try to find the quiz that was deleted
        Optional<Quiz> deletedQuiz = quizRepository.findById(quiz1.getId());

        assertFalse(deletedQuiz.isPresent());  // The quiz should be deleted
    }
}
