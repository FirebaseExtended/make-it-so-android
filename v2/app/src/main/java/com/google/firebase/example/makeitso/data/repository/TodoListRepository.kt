package com.google.firebase.example.makeitso.data.repository

import com.google.firebase.example.makeitso.data.datasource.TodoListRemoteDataSource
import javax.inject.Inject

class TodoListRepository @Inject constructor(
    private val todoListRemoteDataSource: TodoListRemoteDataSource
) {

    //val data: Flow<TodoList> = todoListRemoteDataSource.lists
}