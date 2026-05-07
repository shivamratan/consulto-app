package com.ratanapps.auth.data.repo

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.ratanapps.auth.data.model.User
import com.ratanapps.auth.data.service.FirebaseAuthService
import com.ratanapps.auth.data.service.FirestoreService
import com.ratanapps.auth.domain.repo.AuthRepository
import com.ratanapps.auth.utils.AuthUtils
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    val firebaseAuthService: FirebaseAuthService,
    val firestoreService: FirestoreService
) : AuthRepository {

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
    ): FirebaseUser? {
        return try {
            val authResult = firebaseAuthService.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user ?: return null

            val user = User(
                uId = firebaseUser.uid,
                name = name,
                email = email,
                createdAt = AuthUtils.getCurrentTimeStamp()
            )

            firestoreService.saveUser(user).await()
            firebaseUser
        } catch (e: Exception) {
            null
        }
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
