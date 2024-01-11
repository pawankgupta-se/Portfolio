package com.example.portfolio.domain.usecases

import com.example.portfolio.domain.models.User
import com.example.portfolio.domain.models.toUserDto
import com.example.portfolio.domain.repositories.UserRepository
import javax.inject.Inject


sealed class Result {
    data class Success(val user: User) : Result()
    data object Loading : Result()
    data class Error(val message: String) : Result()
}

class SignUp @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: User) {
        userRepository.signup(user.toUserDto())
    }

    companion object {
        val TAG = SignUp::class.simpleName
    }
}