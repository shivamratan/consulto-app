package com.ratanapps.auth.ui.feature.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ratanapps.auth.domain.usecase.AuthUseCase
import com.ratanapps.auth.domain.usecase.SignupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

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

    fun togglePasswordVisibility() {
        _signupUIState.value = _signupUIState.value.copy(
            isPasswordVisible = !signupUIState.value.isPasswordVisible
        )
    }

    fun signup() {
        viewModelScope.launch {
            val signupValidationResult = signupUseCase.validateSignupForm(
                signupUIState.value.username,
                signupUIState.value.email,
                signupUIState.value.password
            )

            if (signupValidationResult.isValid) {
                _signupUIState.value = _signupUIState.value.copy(
                    isLoading = true
                )
                val firebaseUser = authUseCase.signup(
                    signupUIState.value.username,
                    signupUIState.value.email,
                    signupUIState.value.password
                )

                if (firebaseUser != null) {
                    _signupUIState.value = _signupUIState.value.copy(
                        isLoading = false,
                        error = null,
                        isSignUpFailed = false,
                        isSuccessfulSignUp = true
                    )
                } else {
                    _signupUIState.value = _signupUIState.value.copy(
                        isLoading = false,
                        error = "Something went wrong",
                        isSignUpFailed = true,
                        isSuccessfulSignUp = false
                    )
                }
            } else {
                _signupUIState.value = _signupUIState.value.copy(
                    usernameError = signupValidationResult.usernameError,
                    emailError = signupValidationResult.emailError,
                    passwordError = signupValidationResult.passwordError
                )
            }
        }
    }

    fun onGoogleSignInClick() {

    }
}

data class SignUpUIState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val isValid: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isPasswordVisible: Boolean = false,
    val isSuccessfulSignUp: Boolean = false,
    val isSignUpFailed: Boolean = false
)