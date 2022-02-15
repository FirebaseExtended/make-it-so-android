package com.example.makeitso.screens.sign_up

sealed class SignUpUiState {
    object InitialState: SignUpUiState()
    object ErrorState : SignUpUiState()
}
