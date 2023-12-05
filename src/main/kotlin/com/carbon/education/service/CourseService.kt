package com.carbon.education.service

import com.carbon.education.dto.CreateCourseRequest
import com.carbon.education.dto.GetCoursesRequest
import com.carbon.education.dto.UpdateCourseRequest
import com.carbon.education.model.Course
import com.carbon.education.model.User
import com.carbon.education.repository.CourseRepository
import com.carbon.education.repository.CourseRepository.Companion.priceGreaterOrEqual
import com.carbon.education.repository.CourseRepository.Companion.priceLessOrEqual
import com.carbon.education.repository.CourseRepository.Companion.typeEqual
import org.springframework.data.jpa.domain.Specification.where
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class CourseService(
    private val courseRepository: CourseRepository,
    private val userService: UserService,
) {

    fun create(request: CreateCourseRequest, auth: Authentication): Course {
        val user = userService.loadUserByUsername(auth.name)
        val course = Course(
            name = request.name,
            text = request.text,
            price = request.price,
            courseType = request.type,
            user = user
        )
        courseRepository.save(course)
        return course
    }

    fun getAll(
        request: GetCoursesRequest
    ): List<Course> = courseRepository.findAll(
        where(
            typeEqual(request.courseType)
                .and(priceGreaterOrEqual(request.priceFrom))
                .and(priceLessOrEqual(request.priceTo))
        )
    )

    fun getAllMy(auth: Authentication): List<Course> {
        return courseRepository.findAllByUserEmail(auth.name)
    }

    fun getOneMy(auth: Authentication, courseId: Long): Course {
        val user = userService.loadUserByUsername(auth.name)
        val course = courseRepository.findById(courseId).orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "There's no course with id=$courseId")
        }

        if (user.id != course.user!!.id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "This operation is forbidden!")
        }

        return course
    }

    fun delete(courseId: Long, auth: Authentication) {
        val course = getOneMy(auth, courseId)
        courseRepository.delete(course)
    }

    fun updateCourse(auth: Authentication,
                     courseId: Long,
                     request: UpdateCourseRequest): Course {
        val user = userService.loadUserByUsername(auth.name)
        val course = courseRepository.findById(courseId).orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "There's no course with such id")
        }

        if (user.id != course.user?.id) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "It's forbidden operation")
        }

        course.name = request.name ?: course.name
        course.text = request.text ?: course.text
        course.price = request.price ?: course.price
        course.courseType = request.courseType ?: course.courseType

        courseRepository.save(course)
        return course
    }
}