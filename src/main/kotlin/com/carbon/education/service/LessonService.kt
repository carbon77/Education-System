package com.carbon.education.service

import com.carbon.education.dto.LessonDto
import com.carbon.education.model.Lesson
import com.carbon.education.model.User
import com.carbon.education.repository.LessonRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class LessonService(
    val lessonRepository: LessonRepository,
    val userDetailsService: UserDetailsService,
    val courseService: CourseService
) {

    fun findAllByCourseId(courseId: Long): List<Lesson> {
        return lessonRepository.findAllByCourse_Id(courseId)
    }

    fun create(auth: Authentication, dto: LessonDto): Lesson {
        if (dto.courseId == null) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }

        val course = courseService.getOneMy(auth, dto.courseId)
        val lesson = Lesson(
            title=dto.title,
            order=dto.order,
            text=dto.text,
            course=course
        )

        lessonRepository.save(lesson)
        return lesson
    }

    fun findOneMy(auth: Authentication, lessonId: Long): Lesson {
        val user = userDetailsService.loadUserByUsername(auth.name) as User
        val lesson = lessonRepository.findById(lessonId).orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "There's no lesson with id=$lessonId")
        }

        if (user.id != lesson.course?.user!!.id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "This operation is forbidden!")
        }

        return lesson
    }

    fun update(auth: Authentication, lessonId: Long, dto: LessonDto): Lesson {
        val lesson = findOneMy(auth, lessonId)

        lesson.title = dto.title ?: lesson.title
        lesson.text = dto.text ?: lesson.text
        lesson.order = dto.order ?: lesson.order

        lessonRepository.save(lesson)
        return lesson
    }

    fun delete(auth: Authentication, lessonId: Long) {
        val lesson = findOneMy(auth, lessonId)
        lessonRepository.delete(lesson)
    }
}