package com.example.makeitso.model

import java.time.LocalDate
import java.time.LocalTime

data class Task(
    val id: String,
    val title: String,
    val priority: Priority,
    val dueDate: LocalDate,
    val dueTime: LocalTime,
    val description: String,
    val url: String,
    val flag: Boolean,
    val completed: Boolean
)
