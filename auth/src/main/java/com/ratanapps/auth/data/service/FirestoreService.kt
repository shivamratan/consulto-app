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

}