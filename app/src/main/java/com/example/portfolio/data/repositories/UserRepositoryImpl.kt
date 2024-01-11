package com.example.portfolio.data.repositories

import com.example.portfolio.data.InMemoryDataStore
import com.example.portfolio.data.models.ProfileDto
import com.example.portfolio.data.models.UserDto
import com.example.portfolio.domain.repositories.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(private val dataStore: InMemoryDataStore):
    UserRepository {
    override suspend fun signup(user: UserDto) {
        dataStore.saveUserData(user)
    }

    override suspend fun getCurrentUserProfile(): ProfileDto? = dataStore.getCurrentUserProfile()
}