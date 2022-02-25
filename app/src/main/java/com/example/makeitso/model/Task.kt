package com.example.makeitso.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Task(
    @PrimaryKey
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
