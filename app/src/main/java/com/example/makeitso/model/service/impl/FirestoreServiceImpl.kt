/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.example.makeitso.model.service.impl

import com.example.makeitso.model.Task
import com.example.makeitso.model.service.FirestoreService
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class FirestoreServiceImpl @Inject constructor() : FirestoreService {
    override suspend fun addListener(
        userId: String,
        onDocumentEvent: (DocumentChange.Type, Task) -> Unit,
        onError: (Throwable) -> Unit
    ): ListenerRegistration {
        val query = Firebase.firestore.collection(TASK_COLLECTION).whereEqualTo(USER_ID, userId)

        val registration = query.addSnapshotListener { value, error ->
            if (error != null) {
                onError(error)
                return@addSnapshotListener
            }

            value?.documentChanges?.forEach {
                onDocumentEvent(it.type, it.document.toObject())
            }
        }

        return registration
    }

    override suspend fun removeListener(listenerRegistration: ListenerRegistration?) {
        listenerRegistration?.remove()
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