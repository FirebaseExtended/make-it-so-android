package com.example.makeitso.model.database.repository

import com.example.makeitso.model.Task
import com.example.makeitso.model.User
import com.example.makeitso.model.database.dao.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class TaskRepo(private val taskDao: TaskDao) {
    fun selectAllForUser(user: User): Flow<List<Task>> {
        return taskDao.selectAllForUser(user.id)
    }

    fun select(id: String): Flow<Task> {
        return taskDao.select(id)
    }

    suspend fun insert(task: Task) {
        Dispatchers.IO.apply { taskDao.insert(task) }
    }

    suspend fun delete(task: Task) {
        Dispatchers.IO.apply { taskDao.delete(task.id) }
    }

    suspend fun deleteAllForUser(user: User) {
        Dispatchers.IO.apply { taskDao.deleteAllForUser(user.id) }
    }

    suspend fun updateCompletion(task: Task, isComplete: Boolean) {
        Dispatchers.IO.apply { taskDao.updateCompletion(isComplete, task.id) }
    }

    suspend fun updateFlag(task: Task, hasFlag: Boolean) {
        Dispatchers.IO.apply { taskDao.updateFlag(hasFlag, task.id) }
    }
}
