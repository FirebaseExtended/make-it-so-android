package com.example.makeitso.model.service.impl

import com.example.makeitso.model.Task
import com.example.makeitso.model.service.FirestoreService
import javax.inject.Inject

class FirestoreServiceImpl @Inject constructor() : FirestoreService {

    private val exampleTask = Task(
        title = "Example Title",
        priority = "Medium",
        dueDate = "Mon, 7 July 2022",
        dueTime = "14:23",
        description = "Example Description",
        url = "",
        flag = true,
        completed = false,
        userId = ""
    )

    override suspend fun getTask(taskId: Long): Task {
        return exampleTask
        //Retrieve Task from Firestore
        //If something goes wrong, throw custom exception
    }

    override suspend fun getTasksForUser(userId: String): List<Task> {
        return listOf(exampleTask, exampleTask.copy(id = 1, title = "Example Title 2"))
        //Retrieve Tasks from specific user in Firestore
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

    override suspend fun updateCompletion(taskId: Long, isComplete: Boolean) {
        //Update Task with new completion value on Firestore
        //If something goes wrong, throw custom exception
    }

    override suspend fun deleteTask(taskId: Long) {
        //Delete Task on Firestore
        //If something goes wrong, throw custom exception
    }
}