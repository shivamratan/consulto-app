package com.ratanapps.auth.data.repo

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.ratanapps.auth.domain.repo.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(): AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Task<FirebaseUser> {
        TODO("Not yet implemented")
    }


    override suspend fun signup(
        name: String,
        email: String,
        password: String
    ): Task<FirebaseUser> {
        TODO("Not yet implemented")
    }

    override suspend fun forgotPassword(email: String): Task<Void> {
        TODO("Not yet implemented")
    }

    override suspend fun googleSignIn(idToken: String): Task<FirebaseUser> {
        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }
}