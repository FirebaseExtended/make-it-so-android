package com.google.firebase.example.makeitso.ui.todoitem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TodoItemScreen(viewModel: TodoItemViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState()
}