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

package com.example.makeitso.model.service

import com.example.makeitso.model.Task
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration

interface StorageService {
    fun addListener(
        userId: String,
        onDocumentEvent: (DocumentChange.Type, Task) -> Unit,
        onError: (Throwable) -> Unit
    ): ListenerRegistration

    fun removeListener(listenerRegistration: ListenerRegistration?)
    fun getTask(taskId: String, onError: (Throwable) -> Unit, onSuccess: (Task) -> Unit)
    fun saveTask(task: Task, onResult: (Throwable?) -> Unit)
    fun deleteTask(taskId: String, onResult: (Throwable?) -> Unit)
    fun deleteAllForUser(userId: String, onResult: (Throwable?) -> Unit)
    fun updateUserId(oldUserId: String, newUserId: String, onResult: (Throwable?) -> Unit)
}
