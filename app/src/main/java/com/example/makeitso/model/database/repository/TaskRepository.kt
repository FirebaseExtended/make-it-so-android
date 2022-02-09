package com.example.makeitso.model.database.repository

import com.example.makeitso.model.Task
import com.example.makeitso.model.User
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun select(id: Long): Flow<Task>
    fun selectAllForUser(user: User): Flow<List<Task>>
    suspend fun insert(task: Task)
    suspend fun delete(task: Task)
    suspend fun deleteAllForUser(user: User)
    suspend fun updateCompletion(task: Task, isComplete: Boolean)
    suspend fun updateFlag(task: Task, hasFlag: Boolean)
}
