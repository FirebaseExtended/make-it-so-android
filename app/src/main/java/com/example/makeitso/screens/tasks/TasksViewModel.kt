package com.example.makeitso.screens.tasks

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.makeitso.common.navigation.EDIT_TASK_SCREEN
import com.example.makeitso.common.navigation.LOGIN_SCREEN
import com.example.makeitso.common.navigation.TASKS_SCREEN
import com.example.makeitso.common.navigation.TASK_ID
import com.example.makeitso.model.Task
import com.example.makeitso.model.database.repository.TaskRepository
import com.example.makeitso.model.service.CrashlyticsService
import com.example.makeitso.model.service.FirestoreService
import com.example.makeitso.model.shared_prefs.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val crashlyticsService: CrashlyticsService,
    private val firestoreService: FirestoreService,
    private val taskRepository: TaskRepository,
    private val sharedPrefs: SharedPrefs
) : ViewModel() {
    var uiState = mutableStateOf(TasksUiState())
        private set

    private val currentState get() = uiState.value

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        uiState.value = currentState.copy(hasError = true)
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(throwable) }
    }

    fun initialize() {
        viewModelScope.launch(exceptionHandler) {
            val tasks = firestoreService.getTasksForUser(sharedPrefs.getCurrentUserId())
            uiState.value = TasksUiState(tasks)
        }
    }

    fun onAddTaskClick(navController: NavHostController) {
        navController.navigate(EDIT_TASK_SCREEN)
    }

    fun onTaskCheckChange(task: Task) {
        viewModelScope.launch(exceptionHandler) {
            firestoreService.updateCompletion(task.id, !task.completed)
            taskRepository.updateCompletion(task.id, !task.completed)

            val updatedTasks = currentState.tasks
                .filter { it.id != task.id }
                .toMutableList()
                .apply { add(task.copy(completed = !task.completed)) }

            uiState.value = currentState.copy(tasks = updatedTasks)
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

            val updatedTasks = currentState.tasks
                .filter { it.id != task.id }
                .toMutableList()
                .apply { add(task.copy(flag = !task.flag)) }

            uiState.value = currentState.copy(tasks = updatedTasks)
        }
    }

    private fun onDeleteTaskClick(task: Task) {
        viewModelScope.launch(exceptionHandler) {
            firestoreService.deleteTask(task.id)
            taskRepository.delete(task.id)

            val updatedTasks = currentState.tasks.filter { it.id != task.id }
            uiState.value = currentState.copy(tasks = updatedTasks)
        }
    }

    fun onSignOutClick(navController: NavHostController) {
        viewModelScope.launch(exceptionHandler) {
            sharedPrefs.deleteCurrentUser()

            navController.navigate(LOGIN_SCREEN) {
                popUpTo(TASKS_SCREEN) { inclusive = true }
            }
        }
    }
}