package com.example.makeitso.screens.login

sealed class LoginUiState {
    object InitialState: LoginUiState()
    object ErrorState : LoginUiState()
}
