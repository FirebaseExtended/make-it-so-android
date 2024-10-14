package com.google.firebase.example.makeitso.ui.todolist

import com.google.firebase.example.makeitso.MainViewModel
import com.google.firebase.example.makeitso.data.repository.TodoItemRepository
import com.google.firebase.example.makeitso.data.repository.TodoListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoListRepository: TodoListRepository,
    private val todoItemRepository: TodoItemRepository
) : MainViewModel()