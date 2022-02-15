package com.example.makeitso.screens.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val hasError: Boolean = false
)
