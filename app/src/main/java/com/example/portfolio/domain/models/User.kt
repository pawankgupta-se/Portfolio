package com.example.portfolio.domain.models

import com.example.portfolio.data.models.UserDto

data class User(
    val avatar: String?,
    val firstname: String?,
    val email: String,
    val password: String,
    val website: String?
)

fun User.toUserDto() = UserDto(
    avatar = this.avatar,
    firstname = this.firstname,
    email = this.email,
    password = this.password,
    website = this.website
)
