package com.example.makeitso.screens.tasks

import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.navigation.EDIT_TASK_SCREEN
import com.example.makeitso.common.navigation.LOGIN_SCREEN
import com.example.makeitso.common.navigation.TASKS_SCREEN
import com.example.makeitso.common.navigation.TASK_ID
import com.example.makeitso.model.Task
import com.example.makeitso.model.database.repository.TaskRepository
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.service.CrashlyticsService
import com.example.makeitso.model.service.FirestoreService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val crashlyticsService: CrashlyticsService,
    private val firestoreService: FirestoreService,
    private val accountService: AccountService,
    private val taskRepository: TaskRepository
) : ViewModel() {
    var tasks = mutableStateOf<List<Task>>(emptyList())
        private set

    val snackbarChannel = Channel<@StringRes Int>(Channel.CONFLATED)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        snackbarChannel.trySend(AppText.generic_error)
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(throwable) }
    }

    fun initialize() {
        viewModelScope.launch(exceptionHandler) {
            tasks.value = firestoreService.getTasksForUser(accountService.getUserId())
        }
    }

    fun onAddTaskClick(navController: NavHostController) {
        navController.navigate(EDIT_TASK_SCREEN)
    }

    fun onTaskCheckChange(task: Task) {
        viewModelScope.launch(exceptionHandler) {
            firestoreService.updateCompletion(task.id, !task.completed)
            taskRepository.updateCompletion(task.id, !task.completed)

            val index = tasks.value.indexOfFirst { it.id == task.id }

            tasks.value = tasks.value
                .filter { it.id != task.id }
                .toMutableList()
                .apply { add(index, task.copy(completed = !task.completed)) }
        }
    }

    fun onTaskActionClick(task: Task, action: String, navController: NavHostController) {
        when (TaskActionOption.getByTitle(action)) {
            TaskActionOption.EditTask -> onEditTaskClick(navController, task)
            TaskActionOption.ToggleFlag -> onFlagTaskClick(task)
            TaskActionOption.DeleteTask -> onDeleteTaskClick(task)
        }
    }

    private fun onEditTaskClick(navController: NavHostController, task: Task) {
        navController.navigate("$EDIT_TASK_SCREEN?$TASK_ID={${task.id}}")
    }

    private fun onFlagTaskClick(task: Task) {
        viewModelScope.launch(exceptionHandler) {
            firestoreService.updateFlag(task.id, !task.flag)
            taskRepository.updateFlag(task.id, !task.flag)

            val index = tasks.value.indexOfFirst { it.id == task.id }

            tasks.value = tasks.value
                .filter { it.id != task.id }
                .toMutableList()
                .apply { add(index, task.copy(flag = !task.flag)) }
        }
    }

    private fun onDeleteTaskClick(task: Task) {
        viewModelScope.launch(exceptionHandler) {
            firestoreService.deleteTask(task.id)
            taskRepository.delete(task.id)

            tasks.value = tasks.value.filter { it.id != task.id }
        }
    }

    fun onSignOutClick(navController: NavHostController) {
        viewModelScope.launch(exceptionHandler) {
            accountService.signOut()

            navController.navigate(LOGIN_SCREEN) {
                popUpTo(TASKS_SCREEN) { inclusive = true }
            }
        }
    }
}