package com.example.LMS.repository;

import com.example.LMS.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByQuizId(Long quizId);
}
