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

package com.example.makeitso.screens.edit_task

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.makeitso.TASK_DEFAULT_ID
import com.example.makeitso.common.ext.idFromParameter
import com.example.makeitso.model.Task
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.service.LogService
import com.example.makeitso.model.service.StorageService
import com.example.makeitso.screens.MakeItSoViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val accountService: AccountService
) : MakeItSoViewModel(logService) {
    var task = mutableStateOf(Task())
        private set

    fun initialize(taskId: String) {
        viewModelScope.launch(showErrorExceptionHandler) {
            if (taskId != TASK_DEFAULT_ID) {
                storageService.getTask(taskId.idFromParameter(), ::onError) {
                    task.value = it
                }
            }
        }
    }

    fun onTitleChange(newValue: String) {
        task.value = task.value.copy(title = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        task.value = task.value.copy(description = newValue)
    }

    fun onUrlChange(newValue: String) {
        task.value = task.value.copy(url = newValue)
    }

    fun onDateChange(newValue: Long) {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(UTC))
        calendar.timeInMillis = newValue
        val newDueDate = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).format(calendar.time)
        task.value = task.value.copy(dueDate = newDueDate)
    }

    fun onTimeChange(hour: Int, minute: Int) {
        val newDueTime = "${hour.toClockPattern()}:${minute.toClockPattern()}"
        task.value = task.value.copy(dueTime = newDueTime)
    }

    fun onFlagToggle(newValue: String)  {
        val newFlagOption = EditFlagOption.getBooleanValue(newValue)
        task.value = task.value.copy(flag = newFlagOption)
    }

    fun onPriorityChange(newValue: String) {
        task.value = task.value.copy(priority = newValue)
    }

    fun onDoneClick(popUpScreen: () -> Unit) {
        viewModelScope.launch(showErrorExceptionHandler) {
            val editedTask = task.value.copy(userId = accountService.getUserId())

            if (editedTask.id.isBlank()) saveTask(editedTask, popUpScreen)
            else updateTask(editedTask, popUpScreen)
        }
    }

    private fun saveTask(task: Task, popUpScreen: () -> Unit) {
        val saveTaskTrace = Firebase.performance.newTrace(SAVE_TASK_TRACE)
        saveTaskTrace.start()

        storageService.saveTask(task) { error ->
            saveTaskTrace.stop()
            if (error == null) popUpScreen() else onError(error)
        }
    }

    private fun updateTask(task: Task, popUpScreen: () -> Unit) {
        val updateTaskTrace = Firebase.performance.newTrace(UPDATE_TASK_TRACE)
        updateTaskTrace.start()

        storageService.updateTask(task) { error ->
            updateTaskTrace.stop()
            if (error == null) popUpScreen() else onError(error)
        }
    }

    private fun Int.toClockPattern(): String {
        return if (this < 10) "0$this" else "$this"
    }

    companion object {
        private const val UTC = "UTC"
        private const val DATE_FORMAT = "EEE, d MMM yyyy"
        private const val SAVE_TASK_TRACE = "saveTask"
        private const val UPDATE_TASK_TRACE = "updateTask"
    }
}
