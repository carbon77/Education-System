package com.carbon.education.dto

import com.carbon.education.model.CourseType

data class UpdateCourseRequest(
    val name: String?,
    val text: String?,
    val price: Double?,
    val courseType: CourseType?
);