package com.example.portfolio.presentation.signup

data class SignUpScreenState(
    val avatar: String = "",
    val firstName: String = "",
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val website: String = ""
)