package com.ratanapps.auth.data.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val uId: String = "",
    val name: String = "",
    val email: String = "",
    val createdAt: String = ""
)