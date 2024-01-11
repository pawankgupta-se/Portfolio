package com.example.portfolio.domain.models

import com.example.portfolio.data.models.ProfileDto

data class Profile(
    val avatar: String?,
    val firstname: String?,
    val email: String,
    val website: String?
)


fun ProfileDto.toProfile() = Profile(
    avatar = this.avatar,
    firstname = this.firstname,
    email = this.email,
    website = this.website
)