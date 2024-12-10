package com.google.firebase.example.makeitso.ui.todolist

import com.google.firebase.example.makeitso.MainViewModel
import com.google.firebase.example.makeitso.data.repository.TodoListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoListRepository: TodoListRepository
) : MainViewModel() {
    private val _uiState = MutableStateFlow<TodoListUiState>(TodoListUiState.Success)
    val uiState: StateFlow<TodoListUiState>
        get() = _uiState.asStateFlow()
}