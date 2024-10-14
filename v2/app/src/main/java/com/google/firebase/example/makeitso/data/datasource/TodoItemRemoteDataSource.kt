package com.google.firebase.example.makeitso.data.datasource

import com.google.firebase.example.makeitso.data.model.TodoItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TodoItemRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun getTodoItems(listId: String): Flow<List<TodoItem>> {
        return firestore.collection(TODO_ITEM_COLLECTION)
            .whereEqualTo(LIST_ID_FIELD, listId)
            .orderBy(CREATED_AT_FIELD, Query.Direction.DESCENDING)
            .dataObjects()
    }

    suspend fun getTodoItem(itemId: String): TodoItem? {
        return firestore.collection(TODO_ITEM_COLLECTION).document(itemId).get().await().toObject()
    }

    suspend fun create(todoItem: TodoItem): String {
        return firestore.collection(TODO_ITEM_COLLECTION).add(todoItem).await().id
    }

    suspend fun update(todoItem: TodoItem) {
        firestore.collection(TODO_ITEM_COLLECTION).document(todoItem.id).set(todoItem).await()
    }

    suspend fun delete(itemId: String) {
        firestore.collection(TODO_ITEM_COLLECTION).document(itemId).delete().await()
    }

    companion object {
        private const val LIST_ID_FIELD = "listId"
        private const val CREATED_AT_FIELD = "createdAt"
        private const val TODO_ITEM_COLLECTION = "todolist"
    }
}