package com.google.firebase.example.makeitso.data.repository

import com.google.firebase.example.makeitso.data.datasource.TodoItemRemoteDataSource

class TodoItemRepository(
    private val todoItemRemoteDataSource: TodoItemRemoteDataSource
) {

    //val data: Flow<TodoItem> = todoItemRemoteDataSource.todos
}