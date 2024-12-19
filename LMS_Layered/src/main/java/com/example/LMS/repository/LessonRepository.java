package com.example.LMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.LMS.entity.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

}
