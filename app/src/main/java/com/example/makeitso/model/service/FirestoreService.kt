package com.example.makeitso.model.service

import com.example.makeitso.model.Task

interface FirestoreService {
    suspend fun getTask(taskId: String, onError: (Throwable) -> Unit, onSuccess: (Task) -> Unit)
    suspend fun getTasksForUser(userId: String, onError: (Throwable) -> Unit, onSuccess: (List<Task>) -> Unit)
    suspend fun saveTask(task: Task, onResult: (Throwable?) -> Unit)
    suspend fun deleteTask(taskId: String, onResult: (Throwable?) -> Unit)
    suspend fun deleteAllForUser(userId: String, onResult: (Throwable?) -> Unit)
    suspend fun updateUserId(oldUserId: String, newUserId: String, onResult: (Throwable?) -> Unit)
}
