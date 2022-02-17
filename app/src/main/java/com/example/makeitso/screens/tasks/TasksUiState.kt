package com.example.makeitso.screens.tasks

import com.example.makeitso.model.Task

data class TasksUiState(
    val tasks: MutableList<Task> = mutableListOf(),
    val hasError: Boolean = false
)
