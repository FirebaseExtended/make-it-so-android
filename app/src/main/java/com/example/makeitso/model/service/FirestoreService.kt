package com.example.makeitso.model.service

import com.example.makeitso.model.Task

interface FirestoreService {
    suspend fun getTask(taskId: Long): Task
    suspend fun getTasksForUser(userId: String): List<Task>
    suspend fun saveTask(task: Task)
    suspend fun updateFlag(taskId: Long, hasFlag: Boolean)
    suspend fun updateCompletion(taskId: Long, isComplete: Boolean)
    suspend fun deleteTask(taskId: Long)
}
