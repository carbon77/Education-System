package com.carbon.education.service

import com.carbon.education.dto.CreatePostRequest
import com.carbon.education.model.Post
import com.carbon.education.model.Thread
import com.carbon.education.model.User
import com.carbon.education.repository.PostRepository
import org.springframework.http.HttpStatus
import org.springframework.orm.jpa.JpaSystemException
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userDetailsService: UserDetailsService,
    private val threadService: ThreadService
) {
    fun create(auth: Authentication, request: CreatePostRequest): Post {
        val user: User = userDetailsService.loadUserByUsername(auth.name) as User
        val thread: Thread = threadService.getOne(request.threadId)
        val post = Post(
            user = user,
            thread = thread,
            text = request.text
        )

        try {
            postRepository.save(post)
        } catch (e: JpaSystemException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "This user is banned in this thread")
        }
        return post
    }

    fun delete(auth: Authentication, postId: Long) {
        val post = postRepository.findById(postId)

        if (!post.isPresent) return

        if (!auth.name.equals(post.get().user!!.email)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "This operation is forbidden for you!")
        }

        postRepository.deleteById(postId)
    }
}