package com.example.portfolio.domain.usecases

import javax.inject.Inject

const val  MIN_CHAR_COUNT = 1

class ValidatePassword @Inject constructor() {

    operator fun invoke(password: String): ValidationResult {
        if(password.length < MIN_CHAR_COUNT) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to consist of at least $MIN_CHAR_COUNT character"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}