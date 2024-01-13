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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val validateEmail: ValidEmail,
    val validatePassword: ValidatePassword,
    val signup: SignUp
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpScreenState())
    val uiState = _uiState.asStateFlow()


    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: SignUpFormEvent) {
        when (event) {
            is SignUpFormEvent.AvatarChanged -> {
                _uiState.value = _uiState.value.copy(avatar = event.avatar)
            }

            is SignUpFormEvent.FirstNameChanged -> {
                _uiState.value = _uiState.value.copy(firstName = event.firstName)
            }

            is SignUpFormEvent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(email = event.email)
            }

            is SignUpFormEvent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(password = event.password)
            }

            is SignUpFormEvent.WebsiteChanged -> {
                _uiState.value = _uiState.value.copy(website = event.website)
            }

            is SignUpFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail(uiState.value.email)
        val passwordResult = validatePassword(uiState.value.password)

        val hasError = listOf(
            emailResult,
            passwordResult,
        ).any { !it.successful }

        if (hasError) {
            _uiState.value = _uiState.value.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
            )
            return
        } else {
            _uiState.value = _uiState.value.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
            )
        }
        viewModelScope.launch {
            signup(
                User(
                    avatar = uiState.value.avatar,
                    firstname = uiState.value.firstName,
                    email = uiState.value.email,
                    password = uiState.value.password,
                    website = uiState.value.website
                )
            )
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

}


sealed class ValidationEvent {
    data object Success : ValidationEvent()
}