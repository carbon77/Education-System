package com.carbon.education.repository;

import com.carbon.education.model.Thread
import org.springframework.data.jpa.repository.JpaRepository

interface ThreadRepository : JpaRepository<Thread, Long> {
    fun findAllByUserEmail(email: String): List<Thread>
}