package com.google.firebase.example.makeitso.ui.todoitem

import com.google.firebase.example.makeitso.MainViewModel
import com.google.firebase.example.makeitso.data.repository.TodoItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TodoItemViewModel @Inject constructor(
    private val todoItemRepository: TodoItemRepository
) : MainViewModel() {
    private val _uiState = MutableStateFlow<TodoItemUIState>(TodoItemUIState.Loading)
    val uiState: StateFlow<TodoItemUIState>
        get() = _uiState.asStateFlow()
}