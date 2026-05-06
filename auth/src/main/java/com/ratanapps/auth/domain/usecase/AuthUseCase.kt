package com.ratanapps.auth.domain.usecase

import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import com.google.android.gms.tasks.Task
import com.ratanapps.auth.domain.repo.AuthRepository


class AuthUseCase @Inject constructor(val authRepository: AuthRepository) {

    suspend fun login(email: String, password: String): Task<FirebaseUser> {
        return authRepository.login(email, password)
    }

    suspend fun signup(): Task<FirebaseUser> {
        return authRepository.signup()
    }

    suspend fun forgotPassword(): Task<Void> {
        return authRepository.forgotPassword()
    }

    suspend fun googleSignIn(): Task<FirebaseUser> {
        return authRepository.googleSignIn()
    }

}