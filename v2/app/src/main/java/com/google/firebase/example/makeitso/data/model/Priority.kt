package com.google.firebase.example.makeitso.data.model

enum class Priority(val title: String, val value: Int) {
    LOW("Low", 2),
    MEDIUM("Medium", 1),
    HIGH("High", 0)
}

fun Int.getPriority(): Priority {
    return Priority.entries.first { this == it.value }
}