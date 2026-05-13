package com.ratanapps.auth.domain.usecase

import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import com.google.android.gms.tasks.Task
import com.ratanapps.auth.domain.repo.AuthRepository


class AuthUseCase @Inject constructor(val authRepository: AuthRepository) {

    suspend fun login(email: String, password: String): FirebaseUser? {
        return authRepository.login(email, password)
    }

    suspend fun signup(name: String, email: String, password: String): FirebaseUser? {
        return authRepository.signup(name, email, password)
    }

    suspend fun forgotPassword(email: String): Task<Void> {
        return authRepository.forgotPassword(email)
    }

    suspend fun googleSignUp(idToken: String): FirebaseUser? {
        return authRepository.googleSignUp(idToken)
    }

    suspend fun googleSignIn(idToken: String): FirebaseUser? {
        return authRepository.googleSignUp(idToken)
    }

}