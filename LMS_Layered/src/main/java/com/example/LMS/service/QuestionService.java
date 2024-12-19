package com.example.LMS.service;

import com.example.LMS.entity.Question;
import com.example.LMS.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    
    public List<Question> getQuestionsByQuiz(Long quizId) {
        return questionRepository.findAllByQuizId(quizId);
    }

 
    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }


    public Question updateQuestion(Long questionId, Question updatedQuestion) {
        Question existingQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        existingQuestion.setQuestionText(updatedQuestion.getQuestionText());
        existingQuestion.setQuestionType(updatedQuestion.getQuestionType());
        existingQuestion.setCorrectAnswer(updatedQuestion.getCorrectAnswer());
        existingQuestion.setQuiz(updatedQuestion.getQuiz());
        
        return questionRepository.save(existingQuestion);
    }

 
    public void deleteQuestion(Long questionId) {
        questionRepository.deleteById(questionId);
    }
}
