package com.carbon.education.controller

import com.carbon.education.model.Post
import com.carbon.education.model.Thread
import com.carbon.education.service.ThreadService
import jakarta.validation.constraints.NotBlank
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/thread")
class ThreadController(
    var threadService: ThreadService
) {
    @GetMapping("/all")
    fun getAll(): ResponseEntity<List<Thread>> =
        ResponseEntity.ok(threadService.getAll())

    @GetMapping
    fun getAllByUsername(auth: Authentication): ResponseEntity<List<Thread>> {
        if (auth.name == null)
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized")
        return ResponseEntity.ok(threadService.findAllByUsername(auth.name))
    }

    @PostMapping
    fun create(auth: Authentication, @RequestBody request: CreateThreadRequest): ResponseEntity<Thread> =
        ResponseEntity.ok(threadService.create(auth, request))

    @GetMapping("/{threadId}")
    fun getOne(@PathVariable threadId: Long): ResponseEntity<GetThreadResponse> =
        ResponseEntity.ok(threadService.getOneWithPosts(threadId))

    @DeleteMapping("/{threadId}")
    fun delete(@PathVariable threadId: Long) =
        ResponseEntity.ok(threadService.delete(threadId))

    data class CreateThreadRequest(
        @field:NotBlank(message = "Title is mandatory")
        val title: String,
        val description: String?
    )

    data class GetThreadResponse (
        val thread: Thread,
        val posts: List<Post>
    )
}