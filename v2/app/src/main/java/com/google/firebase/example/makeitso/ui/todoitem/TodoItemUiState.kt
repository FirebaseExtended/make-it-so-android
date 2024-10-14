package com.google.firebase.example.makeitso.ui.todoitem

import com.google.firebase.example.makeitso.data.model.TodoItem

sealed class TodoItemUIState {
    data object Loading: TodoItemUIState()

    data class Error(val errorMessage: String?): TodoItemUIState()

    data class Success(
        val todoItem: TodoItem
    ): TodoItemUIState()
}