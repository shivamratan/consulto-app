package com.ratanapps.auth.domain.usecase

import com.ratanapps.auth.domain.model.LoginValidationResult
import javax.inject.Inject

class LoginUseCase @Inject constructor() {

    fun validateSignupForm(email:String, password:String): LoginValidationResult {

        var emailError: String? = null;
        var passwordError: String? = null;


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

        val isValid = emailError == null && passwordError == null
        return LoginValidationResult(
            emailError = emailError,
            passwordError = passwordError,
            isValid = isValid
        )
    }

}