package com.google.firebase.example.makeitso.ui.home

import com.google.firebase.example.makeitso.data.model.TodoList

sealed class HomeUiState {
    data object Loading: HomeUiState()

    data object Success: HomeUiState()
}