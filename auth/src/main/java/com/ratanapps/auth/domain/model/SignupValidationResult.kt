package com.ratanapps.auth.domain.model

data class SignupValidationResult(

    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,

    val isValid: Boolean
)