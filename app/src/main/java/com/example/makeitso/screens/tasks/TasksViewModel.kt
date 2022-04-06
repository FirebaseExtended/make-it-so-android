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

package com.example.makeitso.screens.tasks

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.makeitso.model.Task
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.service.LogService
import com.example.makeitso.model.service.StorageService
import com.example.makeitso.screens.MakeItSoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val accountService: AccountService
) : MakeItSoViewModel(logService) {
    var tasks = mutableStateListOf<Task>()
        private set

    fun addListener() {
        viewModelScope.launch(showErrorExceptionHandler) {
            storageService.addListener(accountService.getUserId(), ::onDocumentEvent, ::onError)
        }
    }

    fun removeListener() {
        viewModelScope.launch(showErrorExceptionHandler) { storageService.removeListener() }
    }

    fun onTaskCheckChange(task: Task) {
        viewModelScope.launch(showErrorExceptionHandler) {
            val updatedTask = task.copy(completed = !task.completed)

            storageService.saveTask(updatedTask) { error ->
                if (error != null) onError(error)
            }
        }
    }

    fun onTaskActionClick(openEditTask: (String) -> Unit, task: Task, action: String) {
        when (TaskActionOption.getByTitle(action)) {
            TaskActionOption.EditTask -> openEditTask(task.id)
            TaskActionOption.ToggleFlag -> onFlagTaskClick(task)
            TaskActionOption.DeleteTask -> onDeleteTaskClick(task)
        }
    }

    private fun onFlagTaskClick(task: Task) {
        viewModelScope.launch(showErrorExceptionHandler) {
            val updatedTask = task.copy(flag = !task.flag)

            storageService.saveTask(updatedTask) { error ->
                if (error != null) onError(error)
            }
        }
    }

    private fun onDeleteTaskClick(task: Task) {
        viewModelScope.launch(showErrorExceptionHandler) {
            storageService.deleteTask(task.id) { error ->
                if (error != null) onError(error)
            }
        }
    }

    private fun onDocumentEvent(wasDocumentDeleted: Boolean, task: Task) {
        if (wasDocumentDeleted) tasks.remove(task) else updateTaskInList(task)
    }

    private fun updateTaskInList(task: Task) {
        val index = tasks.indexOfFirst { it.id == task.id }
        if (index < 0) tasks.add(task) else tasks[index] = task
    }
}