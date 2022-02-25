package com.example.makeitso.model.service.impl

import com.example.makeitso.model.Task
import com.example.makeitso.model.database.repository.TaskRepository
import com.example.makeitso.model.service.FirestoreService
import javax.inject.Inject

class FirestoreServiceImpl @Inject constructor(
    private val taskRepository: TaskRepository
) : FirestoreService {
    override suspend fun getTask(taskId: String): Task {
        return taskRepository.getById(taskId)
        //Retrieve Task from Firestore
    }

    override suspend fun getTasksForUser(userId: String): List<Task> {
        return taskRepository.getAllForUser(userId)
        //Retrieve Tasks from specific user in Firestore
    }

    override suspend fun saveTask(task: Task) {
        //Save Task on Firestore
    }

    override suspend fun updateFlag(taskId: String, hasFlag: Boolean) {
        //Update Task with new flag value on Firestore
    }

    override suspend fun updateCompletion(taskId: String, isComplete: Boolean) {
        //Update Task with new completion value on Firestore
    }

    override suspend fun deleteTask(taskId: String) {
        //Delete Task on Firestore
    }
}