package com.google.firebase.example.makeitso.ui.todoitem

import com.google.firebase.example.makeitso.MainViewModel
import com.google.firebase.example.makeitso.data.repository.TodoItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodoItemViewModel @Inject constructor(
    private val todoItemRepository: TodoItemRepository
) : MainViewModel()