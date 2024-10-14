package com.google.firebase.example.makeitso.ui.signup

sealed class SignUpUiState {
    data object Loading: SignUpUiState()

    data class Error(val errorMessage: String?): SignUpUiState()

    data class Success(
        val email: String,
        val password: String,
        val repeatPassword: String
    ): SignUpUiState()
}