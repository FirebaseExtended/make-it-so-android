package com.example.makeitso.model.service.impl

import com.example.makeitso.model.Task
import com.example.makeitso.model.service.FirestoreService
import javax.inject.Inject

class FirestoreServiceImpl @Inject constructor() : FirestoreService {
    override suspend fun getTask(taskId: Long): Task {
        return Task(
            title = "Example Title",
            priority = "Medium",
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

    override suspend fun saveTask(task: Task) {
        //Save Task on Firestore
        //If something goes wrong, throw custom exception
    }

    override suspend fun updateFlag(taskId: Long, hasFlag: Boolean) {
        //Update Task with new flag value on Firestore
        //If something goes wrong, throw custom exception
    }

    override suspend fun deleteTask(taskId: Long) {
        //Delete Task on Firestore
        //If something goes wrong, throw custom exception
    }
}