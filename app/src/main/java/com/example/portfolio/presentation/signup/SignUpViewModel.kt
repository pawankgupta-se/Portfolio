package com.example.portfolio.presentation.signup


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.portfolio.domain.models.User
import com.example.portfolio.domain.usecases.SignUp
import com.example.portfolio.domain.usecases.ValidEmail
import com.example.portfolio.domain.usecases.ValidatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
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
                //using update is thread safe.
                _uiState.update { it.copy(avatar = event.avatar) }
            }

            is SignUpFormEvent.FirstNameChanged -> {
                _uiState.update {
                    it.copy(firstName = event.firstName)
                }
            }

            is SignUpFormEvent.EmailChanged -> {
                _uiState.update { it.copy(email = event.email) }
            }

            is SignUpFormEvent.PasswordChanged -> {
                _uiState.update { it.copy(password = event.password) }
            }

            is SignUpFormEvent.WebsiteChanged -> {
                _uiState.update { it.copy(website = event.website) }
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
            _uiState.update {
                it.copy(
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage,
                )
            }
            return
        } else {
            _uiState.update {
                it.copy(
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage,
                )
            }
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