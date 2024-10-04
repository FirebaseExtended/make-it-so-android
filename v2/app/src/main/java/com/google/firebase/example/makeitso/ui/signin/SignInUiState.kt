package com.google.firebase.example.makeitso.ui.signin

sealed class SignInUiState {
    data object Loading: SignInUiState()

    data class Error(val errorMessage: String?): SignInUiState()

    data object Success: SignInUiState()
}