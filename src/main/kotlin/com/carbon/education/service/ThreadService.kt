package com.carbon.education.service

import com.carbon.education.dto.CreateThreadRequest
import com.carbon.education.dto.GetThreadResponse
import com.carbon.education.dto.ThreadInfo
import com.carbon.education.model.Thread
import com.carbon.education.model.User
import com.carbon.education.repository.PostRepository
import com.carbon.education.repository.ThreadRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ThreadService(
    var threadRepository: ThreadRepository,
    var userService: UserService,
    var postRepository: PostRepository
) {
    fun getAll(): List<Thread> = threadRepository.findAll()
    fun findAllByUsername(username: String): List<Thread> = threadRepository.findAllByUserEmail(username)

    fun create(auth: Authentication, request: CreateThreadRequest): Thread {
        val user: User = userService.loadUserByUsername(auth.name) as User
        val thread = Thread(
            title = request.title,
            description = request.description ?: "",
            user = user
        )
        return threadRepository.save(thread)
    }

    fun getOneWithPosts(threadId: Long): GetThreadResponse {
        val posts = postRepository.findAllByThread_Id(threadId)
        val thread = threadRepository.findById(threadId).orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "There's no thread with id=$threadId")
        }

        return GetThreadResponse(thread, posts)
    }

    fun getOne(threadId: Long): Thread =
        threadRepository.findById(threadId).orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "There's no thread with id=$threadId")
        }

    fun getAllThreadsInfo(
        query: String?
    ): List<ThreadInfo> {
        return threadRepository.findAllInfo(query ?: "")
    }

    fun delete(threadId: Long) = threadRepository.deleteById(threadId)

    fun banUser(threadId: Long, userId: Long) {
        val thread = getOne(threadId)
        val user = userService.getById(userId)
        thread.bannedUsers.add(user)

        threadRepository.save(thread)
    }

    fun unbanUser(threadId: Long, userId: Long) {
        val thread = getOne(threadId)
        val user = userService.getById(userId)
        thread.bannedUsers.remove(user)

        threadRepository.save(thread)
    }
}