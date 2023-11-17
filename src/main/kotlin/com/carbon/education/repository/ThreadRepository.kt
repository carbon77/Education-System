package com.carbon.education.repository;

import com.carbon.education.dto.ThreadInfo
import com.carbon.education.model.Thread
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ThreadRepository : JpaRepository<Thread, Long> {
    fun findAllByUserEmail(email: String): List<Thread>

    @Query(
        nativeQuery = true,
        value = "SELECT " +
                "thread_id AS threadId," +
                "title," +
                "author_name AS authorName," +
                "post_count AS postCount," +
                "created_at AS createdAt," +
                "description," +
                "author_id AS authorId " +
                "FROM get_threads_info() " +
                "WHERE title LIKE %:query% OR author_name LIKE %:query%"
    )
    fun findAllInfo(@Param("query") query: String = ""): List<ThreadInfo>
}