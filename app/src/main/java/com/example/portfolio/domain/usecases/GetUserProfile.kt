package com.example.portfolio.domain.usecases

import com.example.portfolio.domain.models.toProfile
import com.example.portfolio.domain.repositories.UserRepository
import javax.inject.Inject

class GetUserProfile @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun  invoke() =  userRepository.getCurrentUserProfile()?.toProfile()
}