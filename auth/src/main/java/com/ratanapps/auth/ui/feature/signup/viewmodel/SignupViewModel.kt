package com.ratanapps.auth.ui.feature.signup.viewmodel

import androidx.lifecycle.ViewModel
import com.ratanapps.auth.domain.usecase.AuthUseCase
import com.ratanapps.auth.domain.usecase.SignupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class SignupViewModel @Inject constructor(
    val authUseCase: AuthUseCase,
    val signupUseCase: SignupUseCase): ViewModel() {

    private val _signupUIState = MutableStateFlow(SignUpUIState())
    val signupUIState: MutableStateFlow<SignUpUIState> = _signupUIState

    fun onUserNameChange(username: String) {
        _signupUIState.value = _signupUIState.value.copy(username = username)
    }

    fun onEmailChange(email: String) {
        _signupUIState.value = _signupUIState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _signupUIState.value = _signupUIState.value.copy(password = password)
    }

    fun signup() {
        if (signupUseCase.validateSignupForm(signupUIState.value)){

        }
    }






}

data class SignUpUIState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val isValid: Boolean = false
)