package com.ratanapps.auth.domain.repo

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    suspend fun login(email: String, password: String): Task<FirebaseUser>

    suspend fun signup(name: String, email: String, password: String): Task<FirebaseUser>

    suspend fun forgotPassword(email: String): Task<Void>

    suspend fun googleSignIn(idToken: String): Task<FirebaseUser>

    fun logout()
}