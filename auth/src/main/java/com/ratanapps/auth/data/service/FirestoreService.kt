package com.ratanapps.auth.data.service

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.ratanapps.auth.data.model.User
import com.ratanapps.auth.utils.AuthConstant
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreService @Inject constructor(val fireStore: FirebaseFirestore) {

    suspend fun saveUser(user: User) {
         fireStore
            .collection(AuthConstant.FIRESTORE_COLLECTION_USER)
            .document(user.uId)
            .set(user)
            .await()
    }

    suspend fun getUserByEmail(email: String): User? {
        val querySnapshot = fireStore
            .collection(AuthConstant.FIRESTORE_COLLECTION_USER)
            .whereEqualTo("email", email)
            .get()
            .await()

        return querySnapshot.documents.firstOrNull()?.toObject(User::class.java)
    }

    suspend fun getUserById(userId: String): User? {
        val documentSnapshot = fireStore
            .collection(AuthConstant.FIRESTORE_COLLECTION_USER)
            .document(userId)
            .get()
            .await()

        return documentSnapshot.toObject(User::class.java)
    }



}