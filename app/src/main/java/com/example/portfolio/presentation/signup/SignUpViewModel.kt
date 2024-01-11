package com.example.portfolio.presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.portfolio.domain.models.User
import com.example.portfolio.domain.usecases.SignUp
import com.example.portfolio.domain.usecases.ValidEmail
import com.example.portfolio.domain.usecases.ValidatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val validateEmail: ValidEmail,
    val validatePassword: ValidatePassword,
    val signup: SignUp
) : ViewModel() {
    var uiState by mutableStateOf(SignUpScreenState())
        private set


    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: SignUpFormEvent) {
        when (event) {
            is SignUpFormEvent.AvatarChanged -> {
                uiState = uiState.copy(avatar = event.avatar)
            }

            is SignUpFormEvent.FirstNameChanged -> {
                uiState = uiState.copy(firstName = event.firstName)
            }

            is SignUpFormEvent.EmailChanged -> {
                uiState = uiState.copy(email = event.email)
            }

            is SignUpFormEvent.PasswordChanged -> {
                uiState = uiState.copy(password = event.password)
            }

            is SignUpFormEvent.WebsiteChanged -> {
                uiState = uiState.copy(website = event.website)
            }

            is SignUpFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail(uiState.email)
        val passwordResult = validatePassword(uiState.password)

        val hasError = listOf(
            emailResult,
            passwordResult,
        ).any { !it.successful }

        if (hasError) {
            uiState = uiState.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
            )
            return
        } else {
            uiState = uiState.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
            )
        }
        viewModelScope.launch {
            signup(
                User(
                    avatar = uiState.avatar,
                    firstname = uiState.firstName,
                    email = uiState.email,
                    password = uiState.password,
                    website = uiState.website
                )
            )
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

}


sealed class ValidationEvent {
    data object Success : ValidationEvent()
}