package com.example.makeitso.model.service.impl

import com.example.makeitso.model.Task
import com.example.makeitso.model.database.repository.TaskRepository
import com.example.makeitso.model.service.FirestoreService
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class FirestoreServiceImpl @Inject constructor(
    private val taskRepository: TaskRepository
) : FirestoreService {
    override suspend fun getTask(taskId: String): Task {
        Firebase.firestore
            .collection(TASK_COLLECTION)
            .whereEqualTo(TASK_ID, taskId)
            .get()
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
        return taskRepository.getById(taskId)
    }

    override suspend fun getTasksForUser(userId: String): List<Task> {
        Firebase.firestore
            .collection(TASK_COLLECTION)
            .whereEqualTo(USER_ID, userId)
            .get()
            .addOnSuccessListener { result -> for (document in result) { } }
            .addOnFailureListener { exception -> }
        return taskRepository.getAllForUser(userId)
    }

    override suspend fun saveTask(task: Task) {
        Firebase.firestore
            .collection(TASK_COLLECTION)
            .document(task.id)
            .set(task)
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
    }

    override suspend fun deleteTask(taskId: String) {
        Firebase.firestore
            .collection(TASK_COLLECTION)
            .document(taskId)
            .delete()
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
    }

    companion object {
        private const val TASK_COLLECTION = "Task"
        private const val TASK_ID = "id"
        private const val USER_ID = "userId"
    }
}