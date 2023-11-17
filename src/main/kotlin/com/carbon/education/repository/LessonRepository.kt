package com.carbon.education.repository;

import com.carbon.education.model.Lesson
import org.springframework.data.jpa.repository.JpaRepository

interface LessonRepository : JpaRepository<Lesson, Long> {

    fun findAllByCourse_Id(courseId: Long): List<Lesson>
}