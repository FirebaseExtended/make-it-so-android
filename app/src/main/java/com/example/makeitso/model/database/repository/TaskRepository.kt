package com.example.makeitso.model.database.repository

import com.example.makeitso.model.Task

interface TaskRepository {
    suspend fun insert(task: Task)
    suspend fun delete(taskId: String)
    suspend fun getById(taskId: String): Task
    suspend fun getAllForUser(userId: String): List<Task>
    suspend fun updateCompletion(taskId: String, isComplete: Boolean)
    suspend fun updateFlag(taskId: String, hasFlag: Boolean)
    suspend fun deleteAllForUser(userId: String)
}
