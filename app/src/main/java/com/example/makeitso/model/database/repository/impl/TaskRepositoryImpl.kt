package com.example.makeitso.model.database.repository.impl

import com.example.makeitso.model.Task
import com.example.makeitso.model.User
import com.example.makeitso.model.database.dao.TaskDao
import com.example.makeitso.model.database.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val taskDao: TaskDao) : TaskRepository {
    override fun select(id: Long): Flow<Task> {
        return taskDao.select(id)
    }

    override fun selectAllForUser(user: User): Flow<List<Task>> {
        return taskDao.selectAllForUser(user.id)
    }

    override suspend fun insert(task: Task) {
        Dispatchers.IO.apply { taskDao.insert(task) }
    }

    override suspend fun delete(task: Task) {
        Dispatchers.IO.apply { taskDao.delete(task.id) }
    }

    override suspend fun deleteAllForUser(user: User) {
        Dispatchers.IO.apply { taskDao.deleteAllForUser(user.id) }
    }

    override suspend fun updateCompletion(task: Task, isComplete: Boolean) {
        Dispatchers.IO.apply { taskDao.updateCompletion(isComplete, task.id) }
    }

    override suspend fun updateFlag(task: Task, hasFlag: Boolean) {
        Dispatchers.IO.apply { taskDao.updateFlag(hasFlag, task.id) }
    }
}
