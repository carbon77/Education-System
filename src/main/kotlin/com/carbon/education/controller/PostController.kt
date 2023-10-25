package com.carbon.education.controller

import com.carbon.education.dto.CreatePostRequest
import com.carbon.education.model.Post
import com.carbon.education.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

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
}