package com.example.portfolio.domain.usecases

import javax.inject.Inject

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)

class ValidEmail @Inject constructor() {
    operator fun invoke(email: String): ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email can't be blank"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}

