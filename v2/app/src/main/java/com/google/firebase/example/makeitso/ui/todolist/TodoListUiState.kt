package com.google.firebase.example.makeitso.ui.todolist

import com.google.firebase.example.makeitso.data.model.TodoItem
import com.google.firebase.example.makeitso.data.model.TodoList

sealed class TodoListUIState {
    data object Loading: TodoListUIState()

    data class Error(val errorMessage: String?): TodoListUIState()

    data class Success(
        val todoList: TodoList,
        val todoItems: List<TodoItem>
    ): TodoListUIState()
}