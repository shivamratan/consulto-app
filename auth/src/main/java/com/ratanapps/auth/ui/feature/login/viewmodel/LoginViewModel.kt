package com.ratanapps.auth.ui.feature.login.viewmodel

import android.content.Context
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.ratanapps.auth.domain.usecase.AuthUseCase
import com.ratanapps.auth.domain.usecase.LoginUseCase
import com.ratanapps.auth.domain.usecase.SignupUseCase
import com.ratanapps.auth.ui.googleauth.GoogleAuthUIProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    val authUseCase: AuthUseCase,
    val loginUseCase: LoginUseCase,
    val googleAuthUIProvider: GoogleAuthUIProvider): ViewModel() {

    private val _loginUIState = MutableStateFlow(LogInUIState())
    val loginUIState: StateFlow<LogInUIState> = _loginUIState

    fun onEmailChange(email: String) {
        _loginUIState.value = _loginUIState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _loginUIState.value = _loginUIState.value.copy(password = password)
    }

    fun togglePasswordVisibility() {
        _loginUIState.value = _loginUIState.value.copy(
            isPasswordVisible = !loginUIState.value.isPasswordVisible
        )
    }


    fun login() {
        viewModelScope.launch {
            val loginValidationResult = loginUseCase.validateSignupForm(
                loginUIState.value.email,
                loginUIState.value.password
            )

            if (loginValidationResult.isValid) {
                _loginUIState.value = _loginUIState.value.copy(
                    isLoading = true
                )

                // firebase logic
                val firebaseUser: FirebaseUser? =
                    authUseCase.login(loginUIState.value.email, loginUIState.value.password)

                if (firebaseUser != null) {
                    _loginUIState.value = _loginUIState.value.copy(
                        isLoading = false,
                        error = null,
                        isLoginFailed = false,
                        isSuccessfulLogin = true
                    )
                } else {
                    _loginUIState.value = _loginUIState.value.copy(
                        isLoading = false,
                        error = "Something went wrong",
                        isLoginFailed = true,
                        isSuccessfulLogin = false
                    )
                }
            } else {
                _loginUIState.value = _loginUIState.value.copy(
                    emailError = loginValidationResult.emailError,
                    passwordError = loginValidationResult.passwordError
                )
            }
        }
    }

    fun onGoogleSignInClick(context: Context) {
        viewModelScope.launch {

            try {
                _loginUIState.value = _loginUIState.value.copy(isLoading = true)
                val googleIdToken = googleAuthUIProvider.googleSignIn(context)
                if (googleIdToken != null) {
                    val firebaseUser = authUseCase.googleSignIn(googleIdToken)

                    if (firebaseUser != null) {
                        _loginUIState.value = _loginUIState.value.copy(
                            isLoading = false,
                            error = null,
                            isLoginFailed = false,
                            isSuccessfulLogin = true
                        )
                    } else {
                        _loginUIState.value = _loginUIState.value.copy(
                            isLoading = false,
                            error = "Something went wrong",
                            isLoginFailed = true,
                            isSuccessfulLogin = false
                        )
                    }
                } else {
                    _loginUIState.value = _loginUIState.value.copy(
                        isLoading = false
                    )
                }
            } catch (e: GetCredentialException) {
                _loginUIState.value = _loginUIState.value.copy(
                    isLoading = false,
                    showNoAccountError = true
                )
            } catch (e: Exception) {
                _loginUIState.value = _loginUIState.value.copy(
                    isLoading = false
                )
            }
        }
    }


    fun dismissNoAccountError() {
        _loginUIState.value = _loginUIState.value.copy(showNoAccountError = false)
    }

    fun dismissNoLoginRecordFoundError() {
        _loginUIState.value = _loginUIState.value.copy(showNoLoginRecordFound = false)
    }


}

data class LogInUIState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isValid: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isPasswordVisible: Boolean = false,
    val isSuccessfulLogin: Boolean = false,
    val isLoginFailed: Boolean = false,
    val showNoAccountError: Boolean = false,
    val showNoLoginRecordFound: Boolean = false
)