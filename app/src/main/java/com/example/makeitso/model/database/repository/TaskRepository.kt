package com.example.makeitso.model.database.repository

import com.example.makeitso.model.Task

interface TaskRepository {
    suspend fun insert(task: Task)
    suspend fun delete(taskId: Long)
    suspend fun updateCompletion(taskId: Long, isComplete: Boolean)
    suspend fun updateFlag(taskId: Long, hasFlag: Boolean)
}
