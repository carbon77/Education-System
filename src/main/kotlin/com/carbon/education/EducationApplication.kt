package com.carbon.education

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [ErrorMvcAutoConfiguration::class])
class EducationApplication

fun main(args: Array<String>) {
	runApplication<EducationApplication>(*args)
}
