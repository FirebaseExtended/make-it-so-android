package com.google.firebase.example.makeitso.ui.todoitem

sealed class TodoItemUIState {
    data object Loading: TodoItemUIState()

    data class Error(val errorMessage: String?): TodoItemUIState()

    data object Success: TodoItemUIState()
}