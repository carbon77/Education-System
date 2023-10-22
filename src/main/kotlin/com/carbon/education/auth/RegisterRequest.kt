package com.carbon.education.auth

data class RegisterRequest(
    val firstName: String,
    val lastName: String? = null,
    val email: String,
    val password: String
)
