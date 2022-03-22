package com.example.makeitso.screens.tasks

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makeitso.common.snackbar.SnackbarManager
import com.example.makeitso.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.makeitso.R.string as AppText
import com.example.makeitso.model.Task
import com.example.makeitso.model.database.repository.TaskRepository
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.service.CrashlyticsService
import com.example.makeitso.model.service.FirestoreService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val crashlyticsService: CrashlyticsService,
    private val firestoreService: FirestoreService,
    private val accountService: AccountService,
    private val taskRepository: TaskRepository
) : ViewModel() {
    var tasks = mutableStateListOf<Task>()
        private set

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        SnackbarManager.showMessage(AppText.generic_error)
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(throwable) }
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            firestoreService.getTasksForUser(accountService.getUserId(), ::onError) {
                tasks.addAll(it)
            }
        }
    }

    fun onTaskCheckChange(task: Task) {
        viewModelScope.launch(exceptionHandler) {
            val updatedTask = task.copy(completed = !task.completed)

            firestoreService.saveTask(updatedTask) { error ->
                if (error == null) {
                    this.launch { taskRepository.updateCompletion(task.id, updatedTask.completed) }
                    updateTaskInList(updatedTask)
                } else onError(error)
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
        viewModelScope.launch(exceptionHandler) {
            val updatedTask = task.copy(flag = !task.flag)

            firestoreService.saveTask(updatedTask) { error ->
                if (error == null) {
                    this.launch { taskRepository.updateFlag(task.id, updatedTask.flag) }
                    updateTaskInList(updatedTask)
                } else onError(error)
            }
        }
    }

    private fun updateTaskInList(task: Task) {
        val index = tasks.indexOfFirst { it.id == task.id }
        tasks[index] = task
    }

    private fun onDeleteTaskClick(task: Task) {
        viewModelScope.launch(exceptionHandler) {
            firestoreService.deleteTask(task.id) { error ->
                if (error == null) {
                    this.launch { taskRepository.delete(task.id) }
                    tasks.remove(task)
                } else onError(error)
            }
        }
    }

    private fun onError(error: Throwable?) {
        SnackbarManager.showMessage(error.toSnackbarMessage())
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(error) }
    }
}