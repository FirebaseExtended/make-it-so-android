package com.google.firebase.example.makeitso.ui.todoitem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.example.makeitso.ui.shared.LoadingIndicator
import kotlinx.serialization.Serializable

@Serializable
data class TodoItemRoute(val itemId: String)

@Composable
fun TodoItemScreen(viewModel: TodoItemViewModel = hiltViewModel()) {
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    if (isLoading) LoadingIndicator()
    else TodoItemScreenContent(viewModel)
}

@Composable
fun TodoItemScreenContent(viewModel: TodoItemViewModel) {

}