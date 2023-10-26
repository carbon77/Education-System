package com.carbon.education.repository;

import com.carbon.education.model.Course
import com.carbon.education.model.CourseType
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface CourseRepository : JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    fun findAllByUser_Id(userId: Long): List<Course>

    companion object {
        fun typeEqual(courseType: CourseType?): Specification<Course> =
            Specification { course, query, cb -> cb.equal(course.get<CourseType>("courseType"), courseType) }

        fun priceLessOrEqual(price: Double?): Specification<Course> =
            Specification { course, query, cb -> cb.lessThanOrEqualTo(course.get("price"), price ?: Double.MAX_VALUE) }

        fun priceGreaterOrEqual(price: Double?): Specification<Course> =
            Specification { course, query, cb -> cb.greaterThanOrEqualTo(course.get("price"), price ?: Double.MIN_VALUE) }
    }
}