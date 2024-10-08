package com.google.firebase.example.makeitso.data.model

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId val id: String = "",
    val email: String = "",
    val displayName: String = "",
    val isAnonymous: Boolean = true
)