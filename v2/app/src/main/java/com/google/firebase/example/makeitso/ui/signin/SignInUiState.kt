package com.google.firebase.example.makeitso.ui.signin

sealed class SignInUiState {
    data object Loading: SignInUiState()

    data object Success: SignInUiState()
}