package com.example.makeitso.screens.tasks

import com.example.makeitso.model.Task

data class TasksUiState(
    val tasks: List<Task> = emptyList(),
    val hasError: Boolean = false
)
