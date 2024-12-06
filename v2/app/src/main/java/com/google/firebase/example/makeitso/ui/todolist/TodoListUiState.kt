package com.google.firebase.example.makeitso.ui.todolist

import com.google.firebase.example.makeitso.data.model.TodoList

sealed class TodoListUiState {
    data object Loading: TodoListUiState()

    data class Error(val errorMessage: String?): TodoListUiState()

    data class Success(
        val todoList: TodoList
    ): TodoListUiState()
}