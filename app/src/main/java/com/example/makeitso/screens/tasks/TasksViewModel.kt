package com.example.makeitso.screens.tasks

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makeitso.common.snackbar.SnackbarManager
import com.example.makeitso.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.makeitso.R.string as AppText
import com.example.makeitso.model.Task
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.service.CrashlyticsService
import com.example.makeitso.model.service.FirestoreService
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentChange.Type.*
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val crashlyticsService: CrashlyticsService,
    private val firestoreService: FirestoreService,
    private val accountService: AccountService
) : ViewModel() {
    var tasks = mutableStateListOf<Task>()
        private set

    private var listenerRegistration: ListenerRegistration? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        SnackbarManager.showMessage(AppText.generic_error)
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(throwable) }
    }

    fun addListener() {
        viewModelScope.launch(exceptionHandler) {
            listenerRegistration = firestoreService.addListener(
                accountService.getUserId(), ::onDocumentEvent, ::onError
            )
        }
    }

    fun removeListener() {
        viewModelScope.launch(exceptionHandler) {
            firestoreService.removeListener(listenerRegistration)
        }
    }

    fun onTaskCheckChange(task: Task) {
        viewModelScope.launch(exceptionHandler) {
            val updatedTask = task.copy(completed = !task.completed)

            firestoreService.saveTask(updatedTask) { error ->
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
        viewModelScope.launch(exceptionHandler) {
            val updatedTask = task.copy(flag = !task.flag)

            firestoreService.saveTask(updatedTask) { error ->
                if (error != null) onError(error)
            }
        }
    }

    private fun onDeleteTaskClick(task: Task) {
        viewModelScope.launch(exceptionHandler) {
            firestoreService.deleteTask(task.id) { error ->
                if (error != null) onError(error)
            }
        }
    }

    private fun onDocumentEvent(type: DocumentChange.Type, task: Task) {
        if (type == REMOVED) tasks.remove(task) else updateTaskInList(task)
    }

    private fun updateTaskInList(task: Task) {
        val index = tasks.indexOfFirst { it.id == task.id }
        if (index < 0) tasks.add(task) else tasks[index] = task
    }

    private fun onError(error: Throwable?) {
        SnackbarManager.showMessage(error.toSnackbarMessage())
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(error) }
    }
}