package com.example.portfolio.domain.repositories

import com.example.portfolio.data.models.ProfileDto
import com.example.portfolio.data.models.UserDto

interface UserRepository {
    suspend fun signup(user: UserDto)
    suspend fun getCurrentUserProfile(): ProfileDto?
}