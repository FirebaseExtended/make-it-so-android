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
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await

class StorageServiceImpl @Inject constructor(private val auth: AccountService) : StorageService {

  override val tasks: Flow<List<Task>>
    get() =
      auth.currentUser.flatMapLatest { user ->
        currentCollection(user.id).snapshots().map { snapshot -> snapshot.toObjects() }
      }

  override suspend fun getTask(taskId: String): Task? =
    currentCollection(auth.currentUserId).document(taskId).get().await().toObject()

  override suspend fun save(task: Task): Unit =
    trace(SAVE_TASK_TRACE) { currentCollection(auth.currentUserId).add(task).await() }

  override suspend fun update(task: Task): Unit =
    trace(UPDATE_TASK_TRACE) {
      currentCollection(auth.currentUserId).document(task.id).set(task).await()
    }

  override suspend fun delete(taskId: String) {
    currentCollection(auth.currentUserId).document(taskId).delete().await()
  }

  // TODO: It's not recommended to delete on the client:
  // https://firebase.google.com/docs/firestore/manage-data/delete-data#kotlin+ktx_2
  override suspend fun deleteAllForUser(userId: String) {
    val matchingTasks = currentCollection(userId).get().await()
    matchingTasks.map { it.reference.delete().asDeferred() }.awaitAll()
  }

  private fun currentCollection(uid: String): CollectionReference =
    Firebase.firestore.collection(USER_COLLECTION).document(uid).collection(TASK_COLLECTION)

  companion object {
    private const val USER_COLLECTION = "users"
    private const val TASK_COLLECTION = "tasks"
    private const val SAVE_TASK_TRACE = "saveTask"
    private const val UPDATE_TASK_TRACE = "updateTask"
  }
}
