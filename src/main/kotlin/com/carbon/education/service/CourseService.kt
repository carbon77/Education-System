package com.carbon.education.service

import com.carbon.education.dto.CreateCourseRequest
import com.carbon.education.dto.GetCoursesRequest
import com.carbon.education.model.Course
import com.carbon.education.model.User
import com.carbon.education.repository.CourseRepository
import com.carbon.education.repository.CourseRepository.Companion.priceGreaterOrEqual
import com.carbon.education.repository.CourseRepository.Companion.priceLessOrEqual
import com.carbon.education.repository.CourseRepository.Companion.typeEqual
import org.apache.tomcat.util.net.openssl.ciphers.Authentication
import org.springframework.data.jpa.domain.Specification.where
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class CourseService(
    private val courseRepository: CourseRepository,
    private val userDetailsService: UserDetailsService
) {

    fun create(request: CreateCourseRequest, auth: Authentication): Course {
        val user = userDetailsService.loadUserByUsername(auth.name) as User
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

    fun getAllByAuthorId(authorId: Long): List<Course> = courseRepository.findAllByUser_Id(authorId)

    fun delete(courseId: Long, auth: Authentication) {
        val user = userDetailsService.loadUserByUsername(auth.name) as User
        val course = courseRepository.findById(courseId).orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "There's no course with id=$courseId")
        }

        if (user.id != course.user!!.id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "This operation is forbidden!")
        }

        courseRepository.delete(course)
    }
}