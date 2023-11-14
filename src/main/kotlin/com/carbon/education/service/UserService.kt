package com.carbon.education.service

import com.carbon.education.dto.UpdateUserInfoRequest
import com.carbon.education.model.User
import com.carbon.education.repository.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails = userRepository.findByEmail(username)
        .orElseThrow { UsernameNotFoundException("User not found") }

    fun updateUserInfo(auth: Authentication, request: UpdateUserInfoRequest) {
        val user = loadUserByUsername(auth.name) as User
        user.firstName = request.firstName
        user.lastName = request.lastName

        userRepository.save(user)
    }
}