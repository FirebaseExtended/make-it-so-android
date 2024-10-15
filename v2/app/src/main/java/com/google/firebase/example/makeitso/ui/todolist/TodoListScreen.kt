package com.google.firebase.example.makeitso.ui.todolist

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.serialization.Serializable

@Serializable
data class TodoListRoute(val listId: String)

@Composable
fun TodoListScreen(viewModel: TodoListViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
}