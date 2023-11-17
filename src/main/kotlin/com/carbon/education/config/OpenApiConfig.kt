package com.carbon.education.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.StringUtils

@Configuration
class OpenApiConfig(
    @param:Value("\${spring.application.name}") private val moduleName: String
) {
    private val apiVersion = "1.0.0"

    @Bean
    fun customOpenAPI(
        @Value("\${server.servlet.context-path}") contextPath: String?
    ): OpenAPI {
        val securitySchemeName = "bearerAuth"
        val apiTitle = String.format(
            "%s API", StringUtils.capitalize(
                moduleName
            )
        )
        return OpenAPI()
            .addServersItem(Server().url(contextPath))
            .addSecurityItem(SecurityRequirement().addList(securitySchemeName))
            .components(
                Components()
                    .addSecuritySchemes(
                        securitySchemeName,
                        SecurityScheme()
                            .name(securitySchemeName)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            )
            .info(Info().title(apiTitle).version(apiVersion))
    }
}