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
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.service.StorageService
import com.example.makeitso.model.service.trace
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(private val auth: AccountService) : StorageService {

    override val tasks: Flow<List<Task>>
        get() = auth.currentUser.flatMapLatest { user ->
            Firebase.firestore.collection(TASK_COLLECTION).whereEqualTo(
                USER_ID, user.id
            ).snapshots()
                .map { snapshot -> snapshot.toObjects() }
        }

    override suspend fun getTask(taskId: String): Task? =
        Firebase.firestore.collection(TASK_COLLECTION).document(taskId).get().await().toObject()

    override suspend fun save(task: Task): Unit = trace(SAVE_TASK_TRACE) {
        val newTask =
            if (task.userId.isEmpty()) task.copy(userId = auth.currentUserId) else task
        Firebase.firestore.collection(TASK_COLLECTION).add(newTask).await()
    }

    override suspend fun update(task: Task): Unit = trace(UPDATE_TASK_TRACE) {
        Firebase.firestore.collection(TASK_COLLECTION).document(task.id).set(task).await()
    }

    override suspend fun delete(taskId: String) {
        Firebase.firestore
            .collection(TASK_COLLECTION)
            .document(taskId)
            .delete().await()
    }

    override suspend fun deleteAllForUser(userId: String) {
        val matchingTasks = Firebase.firestore
            .collection(TASK_COLLECTION)
            .whereEqualTo(USER_ID, userId)
            .get().await()
        matchingTasks.map { it.reference.delete().asDeferred() }.awaitAll()
    }

    override suspend fun updateUserId(oldUserId: String, newUserId: String) {
        val matchingTasks = Firebase.firestore
            .collection(TASK_COLLECTION)
            .whereEqualTo(USER_ID, oldUserId)
            .get().await()

        matchingTasks.map { it.reference.update(USER_ID, newUserId).asDeferred() }.awaitAll()
    }

    companion object {
        private const val TASK_COLLECTION = "Task"
        private const val USER_ID = "userId"
        private const val SAVE_TASK_TRACE = "saveTask"
        private const val UPDATE_TASK_TRACE = "updateTask"
    }
}
