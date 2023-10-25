package com.carbon.education.dto

import jakarta.validation.constraints.NotBlank

data class CreateThreadRequest(
    @field:NotBlank(message = "Title is mandatory")
    val title: String,
    val description: String?
)
