package com.example.portfolio.presentation.signupsuccess

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.portfolio.domain.usecases.GetUserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpSuccessViewModel @Inject constructor(getUserProfile: GetUserProfile) : ViewModel() {
    var uiState by mutableStateOf(SignUpSuccessState())
        private set

    init {
        viewModelScope.launch {
            val profile = getUserProfile()
            profile?.let {
                uiState = uiState.copy(
                    avatar = it.avatar ?: "",
                    firstName = it.firstname ?: "",
                    email = it.email,
                    website = it.website ?: ""
                )
            }
        }
    }

}