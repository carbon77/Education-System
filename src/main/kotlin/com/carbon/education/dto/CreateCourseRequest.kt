package com.carbon.education.dto

import com.carbon.education.model.CourseType

data class CreateCourseRequest(
    val name: String,
    val text: String,
    val price: Double,
    val type: CourseType
)
