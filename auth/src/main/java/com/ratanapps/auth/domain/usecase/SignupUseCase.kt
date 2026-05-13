package com.ratanapps.auth.domain.usecase

import com.ratanapps.auth.domain.model.SignupValidationResult
import com.ratanapps.auth.ui.feature.signup.viewmodel.SignUpUIState
import javax.inject.Inject

class SignupUseCase @Inject constructor() {

    fun validateSignupForm(userName:String, email:String, password:String): SignupValidationResult {

        var userNameError: String? = null;
        var emailError: String? = null;
        var passwordError: String? = null;

        if (userName.isBlank()) {
            userNameError = "Username cannot be empty";
        }

        if (email.isBlank()) {
            emailError = "Email cannot be empty";
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailError = "Invalid Email";
        }

        if (password.isBlank()) {
            passwordError = "Password cannot be empty";
        } else if (password.length < 6) {
            passwordError = "Invalid Password, Size can't less than 6";
        }

        val isValid = userNameError == null && emailError == null && passwordError == null
        return SignupValidationResult(
            usernameError = userNameError,
            emailError = emailError,
            passwordError = passwordError,
            isValid = isValid
        )
    }

}