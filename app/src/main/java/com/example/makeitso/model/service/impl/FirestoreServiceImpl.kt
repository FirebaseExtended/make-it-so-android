package com.example.makeitso.model.service.impl

import com.example.makeitso.model.Task
import com.example.makeitso.model.service.FirestoreService
import javax.inject.Inject

class FirestoreServiceImpl @Inject constructor() : FirestoreService {
    override suspend fun getTask(taskId: Long): Task {
        return Task(
            title = "Example Title",
            priority = "Example Medium",
            dueDate = "Mon, 7 July 2022",
            dueTime = "14:23",
            description = "Example Description",
            url = "",
            flag = true,
            completed = false,
            userId = 0
        )
        //Retrieve Task from Firestore
        //If something goes wrong, throw custom exception
    }

    override suspend fun saveTask(task: Task, callback: (Task) -> Unit) {
        //Save Task on Firestore
        //If something goes wrong, throw custom exception
        callback(task)
    }

    override suspend fun updateFlag(
        taskId: Long,
        hasFlag: Boolean,
        callback: (Long, Boolean) -> Unit
    ) {
        //Update Task with new flag value on Firestore
        //If something goes wrong, throw custom exception
        callback(taskId, hasFlag)
    }

    override suspend fun deleteTask(taskId: Long, callback: (Long) -> Unit) {
        //Delete Task on Firestore
        //If something goes wrong, throw custom exception
        callback(taskId)
    }
}