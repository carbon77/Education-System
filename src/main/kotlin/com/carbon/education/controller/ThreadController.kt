package com.carbon.education.controller

import com.carbon.education.dto.BanUserRequest
import com.carbon.education.dto.CreateThreadRequest
import com.carbon.education.dto.GetThreadResponse
import com.carbon.education.dto.ThreadInfo
import com.carbon.education.model.Thread
import com.carbon.education.service.ThreadService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/threads")
@CrossOrigin("http://localhost:3000")
class ThreadController(
    var threadService: ThreadService
) {
    @GetMapping
    fun getAll(): ResponseEntity<List<Thread>> =
        ResponseEntity.ok(threadService.getAll())

    @GetMapping("/my")
    fun getAllByUsername(auth: Authentication): ResponseEntity<List<Thread>> {
        if (auth.name == null)
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized")
        return ResponseEntity.ok(threadService.findAllByUsername(auth.name))
    }

    @GetMapping("/info")
    fun getThreadsInfo(
        @RequestParam(required = false) query: String?
    ): List<ThreadInfo> {
        return threadService.getAllThreadsInfo(query)
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

    @PostMapping("/{threadId}/ban")
    fun banUser(@PathVariable threadId: Long, @RequestBody request: BanUserRequest) =
        ResponseEntity.ok(threadService.banUser(threadId, request.userId))

    @PostMapping("/{threadId}/unban")
    fun unbanUser(@PathVariable threadId: Long, @RequestBody request: BanUserRequest) =
        ResponseEntity.ok(threadService.unbanUser(threadId, request.userId))
}