package com.example.makeitso.screens.edit_task

import com.example.makeitso.model.Task

data class EditTaskUiState(
    val task: Task = Task(),
    val hasError: Boolean = false
)
