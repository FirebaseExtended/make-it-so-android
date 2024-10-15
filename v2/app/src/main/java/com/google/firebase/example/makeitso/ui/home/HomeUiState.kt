package com.google.firebase.example.makeitso.ui.home

import com.google.firebase.example.makeitso.data.model.TodoList

sealed class HomeUiState {
    data object Loading: HomeUiState()

    data class Error(val errorMessage: String?): HomeUiState()

    data class Success(
        val todoLists: List<TodoList>
    ): HomeUiState()
}