package com.example.portfolio.data

import com.example.portfolio.data.models.ProfileDto
import com.example.portfolio.data.models.UserDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryDataStore @Inject constructor() {
    private var currentUserData: UserDto? = null

   suspend fun getCurrentUserProfile(): ProfileDto? = currentUserData?.let {
        ProfileDto(
            avatar = it.avatar,
            firstname = it.firstname,
            email = it.email,
            website = it.website
        )
    }

    suspend fun saveUserData(userDto: UserDto) {
        currentUserData = userDto
    }

}
