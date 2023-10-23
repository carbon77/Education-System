package com.carbon.education.repository;

import com.carbon.education.model.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {
    fun findAllByThread_Id(threadId: Long): List<Post>
}