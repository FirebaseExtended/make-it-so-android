package com.google.firebase.example.makeitso.ui.signup

sealed class SignUpUiState {
    data object Loading: SignUpUiState()

    data class Error(val errorMessage: String?): SignUpUiState()

    data object Success: SignUpUiState()
}