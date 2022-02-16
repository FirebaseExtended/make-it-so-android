package com.example.makeitso.model.database.repository

import com.example.makeitso.model.Task
import com.example.makeitso.model.User
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun select(id: Long): Flow<Task>
    suspend fun selectAllForUser(userId: Long): Flow<List<Task>>
    suspend fun insert(task: Task)
    suspend fun delete(task: Task)
    suspend fun deleteAllForUser(user: User)
    suspend fun updateCompletion(task: Task, isComplete: Boolean)
    suspend fun updateFlag(task: Task, hasFlag: Boolean)
}
