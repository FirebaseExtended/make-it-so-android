package com.google.firebase.example.makeitso.data.model

import com.google.firebase.firestore.DocumentId

//TODO: Start using this data class when we add profile features to the Settings screen
data class User(
    @DocumentId val id: String = "",
    val email: String = "",
    val displayName: String = "",
    val isAnonymous: Boolean = true
)