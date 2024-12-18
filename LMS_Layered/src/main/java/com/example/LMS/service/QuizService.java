package com.example.LMS.service;

import com.example.LMS.entity.Quiz;
import com.example.LMS.entity.Question;
import com.example.LMS.repository.QuizRepository;
import com.example.LMS.repository.QuestionRepository;
import com.example.LMS.utils.NotificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private NotificationUtils notificationUtil;

    public Quiz createQuiz(Quiz quiz) {
        Quiz createdQuiz = quizRepository.save(quiz);
        notificationUtil.sendNotificationToInstructors(
                "New quiz created: " + createdQuiz.getTitle(),
                createdQuiz.getCourse().getId()
        );
        return createdQuiz;
    }

    public Question addQuestionToQuiz(Long quizId, Question question) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        question.setQuiz(quiz);
        return questionRepository.save(question);
    }

    public List<Question> getQuestionsForQuiz(Long quizId) {
        return questionRepository.findAllByQuizId(quizId);
    }
}
