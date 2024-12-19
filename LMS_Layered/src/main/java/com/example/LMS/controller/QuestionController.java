package com.example.LMS.controller;

import com.example.LMS.entity.Question;
import com.example.LMS.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

   
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<Question>> getQuestionsByQuiz(@PathVariable Long quizId) {
        List<Question> questions = questionService.getQuestionsByQuiz(quizId);
        return ResponseEntity.ok(questions);
    }

   
    @PostMapping
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        Question savedQuestion = questionService.addQuestion(question);
        return ResponseEntity.ok(savedQuestion);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(
            @PathVariable("id") Long questionId,
            @RequestBody Question updatedQuestion) {
        Question question = questionService.updateQuestion(questionId, updatedQuestion);
        return ResponseEntity.ok(question);
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable("id") Long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok("Question deleted successfully!");
    }
}
