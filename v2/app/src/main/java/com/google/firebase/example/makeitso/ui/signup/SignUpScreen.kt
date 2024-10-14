package com.google.firebase.example.makeitso.ui.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.serialization.Serializable

@Serializable
object SignUpRoute

@Composable
fun SignUpScreen(viewModel: SignUpViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState()
}