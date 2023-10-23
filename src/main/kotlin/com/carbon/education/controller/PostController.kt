package com.carbon.education.controller

import com.carbon.education.model.Post
import com.carbon.education.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/post")
class PostController(
    private val postService: PostService
) {

    @PostMapping
    fun create(auth: Authentication, @RequestBody request: CreatePostRequest): ResponseEntity<Post> =
        ResponseEntity.ok(postService.create(auth, request))

    @DeleteMapping("/{postId}")
    fun delete(auth: Authentication, @PathVariable postId: Long) =
        ResponseEntity.ok(postService.delete(auth, postId))

    data class CreatePostRequest(
        val text: String,
        val threadId: Long
    )
}