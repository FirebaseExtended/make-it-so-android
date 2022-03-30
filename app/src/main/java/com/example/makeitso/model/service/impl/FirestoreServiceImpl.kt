package com.example.makeitso.model.service.impl

import com.example.makeitso.model.Task
import com.example.makeitso.model.service.FirestoreService
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class FirestoreServiceImpl @Inject constructor() : FirestoreService {
    override suspend fun addListener(
        userId: String,
        onDocumentEvent: (DocumentChange.Type, Task) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Firebase.firestore
            .collection(TASK_COLLECTION)
            .whereEqualTo(USER_ID, userId)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    onError(error)
                    return@addSnapshotListener
                }

                value?.documentChanges?.forEach {
                    onDocumentEvent(it.type, it.document.toObject())
                }
            }
    }

    override suspend fun getTask(
        taskId: String,
        onError: (Throwable) -> Unit,
        onSuccess: (Task) -> Unit
    ) {
        Firebase.firestore
            .collection(TASK_COLLECTION)
            .document(taskId)
            .get()
            .addOnFailureListener { error -> onError(error) }
            .addOnSuccessListener { result -> onSuccess(result.toObject() ?: Task()) }
    }

    override suspend fun saveTask(task: Task, onResult: (Throwable?) -> Unit) {
        Firebase.firestore
            .collection(TASK_COLLECTION)
            .document(task.id)
            .set(task)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override suspend fun deleteTask(taskId: String, onResult: (Throwable?) -> Unit) {
        Firebase.firestore
            .collection(TASK_COLLECTION)
            .document(taskId)
            .delete()
            .addOnCompleteListener { onResult(it.exception) }
    }

    override suspend fun deleteAllForUser(userId: String, onResult: (Throwable?) -> Unit) {
        Firebase.firestore
            .collection(TASK_COLLECTION)
            .whereEqualTo(USER_ID, userId)
            .get()
            .addOnFailureListener { error -> onResult(error) }
            .addOnSuccessListener { result ->
                for (document in result) document.reference.delete()
                onResult(null)
            }
    }

    override suspend fun updateUserId(
        oldUserId: String,
        newUserId: String,
        onResult: (Throwable?) -> Unit
    ) {
        Firebase.firestore
            .collection(TASK_COLLECTION)
            .whereEqualTo(USER_ID, oldUserId)
            .get()
            .addOnFailureListener { error -> onResult(error) }
            .addOnSuccessListener { result ->
                for (document in result) document.reference.update(USER_ID, newUserId)
                onResult(null)
            }
    }

    companion object {
        private const val TASK_COLLECTION = "Task"
        private const val USER_ID = "userId"
    }
}