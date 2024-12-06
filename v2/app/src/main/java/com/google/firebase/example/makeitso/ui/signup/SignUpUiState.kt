package com.google.firebase.example.makeitso.ui.signup

sealed class SignUpUiState {
    data object Loading: SignUpUiState()

    data object Success: SignUpUiState()
}