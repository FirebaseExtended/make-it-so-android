package com.google.firebase.example.makeitso.data.model

import com.google.firebase.firestore.DocumentId

data class TodoItem(
    @DocumentId val id: String = "",
    val title: String = "",
    val description: String = "",
    val completed: Boolean = false
)