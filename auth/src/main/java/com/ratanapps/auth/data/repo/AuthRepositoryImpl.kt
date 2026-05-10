package com.ratanapps.auth.data.repo

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.ratanapps.auth.data.model.User
import com.ratanapps.auth.data.service.FirebaseAuthService
import com.ratanapps.auth.data.service.FirestoreService
import com.ratanapps.auth.domain.repo.AuthRepository
import com.ratanapps.auth.utils.AuthUtils
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    val firebaseAuthService: FirebaseAuthService,
    val firestoreService: FirestoreService,
    val firebaseAuth: FirebaseAuth
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

            firestoreService.saveUser(user)
            firebaseUser
        } catch (e: Exception) {
            Log.e("AuthRepository", "Signup failed", e)
            e.printStackTrace()
            null
        }
    }

    override suspend fun forgotPassword(email: String): Task<Void> {
        TODO("Not yet implemented")
    }

    override suspend fun googleSignIn(idToken: String): FirebaseUser? {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val firebaseUser = authResult.user ?: return null

            val user = User(
                uId = firebaseUser.uid,
                name = firebaseUser.displayName ?: "",
                email = firebaseUser.email ?: "",
                createdAt = AuthUtils.getCurrentTimeStamp()
            )

            firestoreService.saveUser(user)
            firebaseUser
        } catch (exception: Exception) {
            Log.e("AuthRepository", "Google sign-in failed", exception)
            null
        }
    }

    override fun logout() {
        TODO("Not yet implemented")
    }
}
