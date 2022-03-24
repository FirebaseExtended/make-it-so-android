package com.example.makeitso.model

import java.util.UUID

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val priority: String = "",
    val dueDate: String = "",
    val dueTime: String = "",
    val description: String = "",
    val url: String = "",
    val flag: Boolean = false,
    val completed: Boolean = false,
    val userId: String = ""
)
