package com.carbon.education.dto

import com.carbon.education.model.Role

data class UpdateUserInfoRequest(
    val firstName: String?,
    val lastName: String?,
    val role: Role?
)
