package com.carbon.education.dto

import com.carbon.education.model.CourseType

data class GetCoursesRequest(
    val courseType: CourseType? = null,
    val priceFrom: Double? = null,
    val priceTo: Double? = null
)