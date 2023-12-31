package com.carbon.education.service

import com.carbon.education.dto.UpdateUserInfoRequest
import com.carbon.education.model.Role
import com.carbon.education.model.User
import com.carbon.education.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): User = userRepository.findByEmail(username)
        .orElseThrow { UsernameNotFoundException("User not found") }

    fun updateUserInfo(auth: Authentication, request: UpdateUserInfoRequest) {
        val user = loadUserByUsername(auth.name)
        user.firstName = request.firstName ?: user.firstName
        user.lastName = request.lastName ?: user.lastName

        if (request.role != null && request.role != Role.ADMIN) {
            user.role = request.role
        }

        userRepository.save(user)
    }

    fun getById(userId: Long): User = userRepository.findById(userId)
        .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found") }
}