package com.carbon.education.controller

import com.carbon.education.dto.UpdateUserInfoRequest
import com.carbon.education.model.User
import com.carbon.education.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/user")
@CrossOrigin("http://localhost:3000")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun loadUser(auth: Authentication): ResponseEntity<User> {
        if (auth.name == null)
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized")
        return ResponseEntity.ok(userService.loadUserByUsername(auth.name) as User)
    }

    @PutMapping
    fun updateUserInfo(auth: Authentication, @RequestBody request: UpdateUserInfoRequest) {
        if (auth.name == null) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized")
        }

        userService.updateUserInfo(auth, request)
    }
}