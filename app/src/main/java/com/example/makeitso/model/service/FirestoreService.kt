package com.example.makeitso.model.service

import com.example.makeitso.model.Task

interface FirestoreService {
    suspend fun getTask(taskId: Long): Task
    suspend fun saveTask(task: Task, callback: (Task) -> Unit)
    suspend fun updateFlag(taskId: Long, hasFlag: Boolean, callback: (Long, Boolean) -> Unit)
    suspend fun deleteTask(taskId: Long, callback: (Long) -> Unit)
}
