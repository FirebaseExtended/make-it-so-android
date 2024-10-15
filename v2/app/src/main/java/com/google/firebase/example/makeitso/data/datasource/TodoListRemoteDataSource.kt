package com.google.firebase.example.makeitso.data.datasource

import com.google.firebase.example.makeitso.data.model.TodoList
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TodoListRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun getTodoLists(userId: String): Flow<List<TodoList>> {
        return firestore.collection(TODO_LIST_COLLECTION)
            .whereEqualTo(USER_ID_FIELD, userId)
            .orderBy(CREATED_AT_FIELD, Query.Direction.DESCENDING)
            .dataObjects()
    }

    suspend fun getTodoList(listId: String): TodoList? {
        return firestore.collection(TODO_LIST_COLLECTION).document(listId).get().await().toObject()
    }

    suspend fun create(todoList: TodoList): String {
        return firestore.collection(TODO_LIST_COLLECTION).add(todoList).await().id
    }

    suspend fun update(todoList: TodoList) {
        firestore.collection(TODO_LIST_COLLECTION).document(todoList.id).set(todoList).await()
    }

    suspend fun delete(listId: String) {
        firestore.collection(TODO_LIST_COLLECTION).document(listId).delete().await()
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val CREATED_AT_FIELD = "createdAt"
        private const val TODO_LIST_COLLECTION = "todolist"
    }
}