package com.google.firebase.example.makeitso.data.repository

import com.google.firebase.example.makeitso.data.datasource.TodoListRemoteDataSource

class TodoListRepository(
    private val todoListRemoteDataSource: TodoListRemoteDataSource
) {

    //val data: Flow<TodoList> = todoListRemoteDataSource.lists
}