package com.carbon.education.controller

import com.carbon.education.dto.LessonDto
import com.carbon.education.model.Lesson
import com.carbon.education.service.LessonService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/lessons")
class LessonController(
    val lessonService: LessonService
) {

    @GetMapping
    fun findAllByCourseId(@RequestParam(required = true) courseId: Long): ResponseEntity<List<Lesson>> {
        return ResponseEntity.ok(lessonService.findAllByCourseId(courseId))
    }

    @PostMapping
    fun create(
        auth: Authentication,
        @RequestBody dto: LessonDto
    ): ResponseEntity<Lesson> {
        return ResponseEntity.ok(lessonService.create(auth, dto))
    }

    @PutMapping("/{lessonId}")
    fun update(
        auth: Authentication,
        @PathVariable lessonId: Long,
        @RequestBody dto: LessonDto
    ): ResponseEntity<Lesson> {
        return ResponseEntity.ok(lessonService.update(auth, lessonId, dto))
    }

    @DeleteMapping("/{lessonId}")
    fun delete(auth: Authentication, @PathVariable lessonId: Long) {
        lessonService.delete(auth, lessonId)
    }
}