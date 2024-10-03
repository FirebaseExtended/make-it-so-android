package com.google.firebase.example.makeitso.data.repository

import com.google.firebase.example.makeitso.data.datasource.TodosDataSource

class TodosRepository(
    private val todosDataSource: TodosDataSource
) {

    //val data: Flow<TodoItem> = todosDataSource.todos
}