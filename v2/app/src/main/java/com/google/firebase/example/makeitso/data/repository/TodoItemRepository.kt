package com.google.firebase.example.makeitso.data.repository

import com.google.firebase.example.makeitso.data.datasource.TodoItemRemoteDataSource
import javax.inject.Inject

class TodoItemRepository @Inject constructor(
    private val todoItemRemoteDataSource: TodoItemRemoteDataSource
) {

    //val data: Flow<TodoItem> = todoItemRemoteDataSource.todos
}