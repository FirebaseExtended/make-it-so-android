package com.example.makeitso.model.service

import com.example.makeitso.model.Task

interface FirestoreService {
    suspend fun getTask(taskId: String): Task
    suspend fun getTasksForUser(userId: String): List<Task>
    suspend fun saveTask(task: Task)
    suspend fun updateFlag(taskId: String, hasFlag: Boolean)
    suspend fun updateCompletion(taskId: String, isComplete: Boolean)
    suspend fun deleteTask(taskId: String)
}
