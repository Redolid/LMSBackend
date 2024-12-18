package com.example.LMS.controller;

import com.example.LMS.entity.Quiz;
import com.example.LMS.entity.Question;
import com.example.LMS.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    @Autowired
    private QuizService quizService;
    
    @GetMapping
	 public String testEndpoint() {
	       return "Testing, From Quiz Component!";
	 }


    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        return ResponseEntity.ok(quizService.createQuiz(quiz));
    }

    @PostMapping("/{quizId}/questions")
    public ResponseEntity<Question> addQuestion(@PathVariable Long quizId, @RequestBody Question question) {
        return ResponseEntity.ok(quizService.addQuestionToQuiz(quizId, question));
    }

    @GetMapping("/{quizId}/questions")
    public ResponseEntity<List<Question>> getQuestions(@PathVariable Long quizId) {
        return ResponseEntity.ok(quizService.getQuestionsForQuiz(quizId));
    }
}
