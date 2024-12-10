package com.google.firebase.example.makeitso.ui.todoitem

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.example.makeitso.ui.shared.LoadingIndicator
import kotlinx.serialization.Serializable

@Serializable
data class TodoItemRoute(val itemId: String)

@Composable
fun TodoItemScreen(viewModel: TodoItemViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.value is TodoItemUIState.Loading) {
        LoadingIndicator()
    } else {
        TodoItemScreenContent()
    }
}

@Composable
fun TodoItemScreenContent(viewModel: TodoItemViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
}