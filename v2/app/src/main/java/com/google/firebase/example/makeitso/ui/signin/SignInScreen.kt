package com.google.firebase.example.makeitso.ui.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SignInScreen(viewModel: SignInViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState()
}