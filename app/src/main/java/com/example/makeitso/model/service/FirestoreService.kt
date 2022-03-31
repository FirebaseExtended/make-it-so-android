package com.example.makeitso.model.service

import com.example.makeitso.model.Task
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration

interface FirestoreService {
    suspend fun addListener(
        userId: String,
        onDocumentEvent: (DocumentChange.Type, Task) -> Unit,
        onError: (Throwable) -> Unit
    ): ListenerRegistration

    suspend fun removeListener(listenerRegistration: ListenerRegistration?)
    suspend fun getTask(taskId: String, onError: (Throwable) -> Unit, onSuccess: (Task) -> Unit)
    suspend fun saveTask(task: Task, onResult: (Throwable?) -> Unit)
    suspend fun deleteTask(taskId: String, onResult: (Throwable?) -> Unit)
    suspend fun deleteAllForUser(userId: String, onResult: (Throwable?) -> Unit)
    suspend fun updateUserId(oldUserId: String, newUserId: String, onResult: (Throwable?) -> Unit)
}
