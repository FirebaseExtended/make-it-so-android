package com.google.firebase.example.makeitso.data.model

import com.google.firebase.firestore.DocumentId

data class TodoItem(
    @DocumentId val id: String = "",
    val title: String = "",
    val priority: Int = Priority.LOW.value,
    val completed: Boolean = false,
    val flagged: Boolean = false,
    val ownerId: String = ""
)

fun TodoItem.isHighPriority(): Boolean {
    return this.priority == Priority.HIGH.value
}

fun TodoItem.isMediumPriority(): Boolean {
    return this.priority == Priority.MEDIUM.value
}

fun TodoItem.isLowPriority(): Boolean {
    return this.priority == Priority.LOW.value
}

