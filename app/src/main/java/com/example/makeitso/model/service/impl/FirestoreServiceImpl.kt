package com.example.makeitso.model.service.impl

import com.example.makeitso.model.Task
import com.example.makeitso.model.service.FirestoreService
import javax.inject.Inject

class FirestoreServiceImpl @Inject constructor() : FirestoreService {
    override suspend fun getTask(taskId: Long): Task {
        return Task(
            title = "Title",
            priority = "Medium",
            dueDate = "Mon, 7 July 2022",
            dueTime = "14:23",
            description = "Description",
            url = " ",
            flag = true,
            completed = false,
            userId = 0
        )
        //Retrieve Task from Firestore
        //If something goes wrong, throw custom exception
    }
}