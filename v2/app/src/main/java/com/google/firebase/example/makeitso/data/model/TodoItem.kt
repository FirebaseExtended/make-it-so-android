package com.google.firebase.example.makeitso.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class TodoItem(
    @DocumentId val id: String = "",
    @ServerTimestamp val createdAt: Date = Date(),
    val title: String = "",
    val priority: String = "",
    val completed: Boolean = false,
    val owner: String = ""
)