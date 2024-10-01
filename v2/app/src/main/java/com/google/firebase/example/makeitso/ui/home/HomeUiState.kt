package com.google.firebase.example.makeitso.ui.home

sealed class HomeUiState {
    data object Loading: HomeUiState()

    data class Error(val errorMessage: String?): HomeUiState()

    data object Success: HomeUiState()
}