package com.google.firebase.example.makeitso.data.model

import com.google.firebase.firestore.DocumentId

data class TodoItem(
    @DocumentId val id: String = "",
    val title: String = "",
    val priority: Int = 0,
    val completed: Boolean = false,
    val flagged: Boolean = false,
    val ownerId: String = ""
)

