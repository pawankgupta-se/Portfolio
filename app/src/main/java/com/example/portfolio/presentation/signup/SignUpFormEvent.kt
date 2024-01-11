package com.example.portfolio.presentation.signup

sealed class SignUpFormEvent {
    data class AvatarChanged(val avatar: String): SignUpFormEvent()
    data class FirstNameChanged(val firstName: String) : SignUpFormEvent()
    data class EmailChanged(val email: String) : SignUpFormEvent()
    data class PasswordChanged(val password: String) : SignUpFormEvent()
    data class WebsiteChanged(val website: String): SignUpFormEvent()

    data object Submit: SignUpFormEvent()
}