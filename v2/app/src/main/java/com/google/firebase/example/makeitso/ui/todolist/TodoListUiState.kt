package com.google.firebase.example.makeitso.ui.todolist

sealed class TodoListUIState {
    data object Loading: TodoListUIState()

    data class Error(val errorMessage: String?): TodoListUIState()

    data object Success: TodoListUIState()
}