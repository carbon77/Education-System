package com.carbon.education.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app.security.jwt")
data class JwtConfig(
    var secretKey: String = "",
    var expiration: Long = 0,
    var refreshTokenExpiration: Long = 0
)
