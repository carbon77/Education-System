package com.carbon.education.model

import org.springframework.security.core.GrantedAuthority

enum class Role {
    USER, TEACHER, ADMIN;

    fun getAuthorities(): List<GrantedAuthority> {
        return ArrayList()
    }
}