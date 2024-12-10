package com.google.firebase.example.makeitso.ui.todolist

sealed class TodoListUiState {
    data object Loading: TodoListUiState()

    data object Success: TodoListUiState()
}