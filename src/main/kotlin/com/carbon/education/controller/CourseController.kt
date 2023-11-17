package com.carbon.education.controller

import com.carbon.education.dto.CreateCourseRequest
import com.carbon.education.dto.GetCoursesRequest
import com.carbon.education.model.Course
import com.carbon.education.model.CourseType
import com.carbon.education.service.CourseService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/courses")
class CourseController(
    private val courseService: CourseService
) {

    @GetMapping
    fun getAllByFilters(
        @RequestParam(required = false) courseType: CourseType?,
        @RequestParam(required = false) priceFrom: Double?,
        @RequestParam(required = false) priceTo: Double?
    ): ResponseEntity<List<Course>> {
        return ResponseEntity.ok(courseService.getAll(GetCoursesRequest(courseType, priceFrom, priceTo)))
    }

    @GetMapping("/my")
    fun getAllMy(auth: Authentication): ResponseEntity<List<Course>> = ResponseEntity.ok(courseService.getAllMy(auth))

    @PostMapping
    fun create(auth: Authentication, @RequestBody request: CreateCourseRequest): ResponseEntity<Course> {
        return ResponseEntity.ok(courseService.create(request, auth))
    }

    @DeleteMapping("/{courseId}")
    fun delete(auth: Authentication, @PathVariable courseId: Long) {
        courseService.delete(courseId, auth)
    }
}