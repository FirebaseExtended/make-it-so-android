package com.example.makeitso.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val title: String,
    val priority: String,
    val dueDate: String,
    val dueTime: String,
    val description: String,
    val url: String,
    val flag: Boolean,
    val completed: Boolean,
    val userId: String
)
