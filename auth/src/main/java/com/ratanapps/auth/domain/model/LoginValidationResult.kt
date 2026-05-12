package com.ratanapps.auth.domain.model

data class LoginValidationResult (
    val emailError: String? = null,
    val passwordError: String? = null,
    val isValid: Boolean
)