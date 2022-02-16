package com.example.makeitso.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.makeitso.common.navigation.EDIT_TASK_SCREEN
import com.example.makeitso.common.navigation.LOGIN_SCREEN
import com.example.makeitso.common.navigation.TASKS_SCREEN
import com.example.makeitso.model.database.repository.TaskRepository
import com.example.makeitso.model.database.repository.UserRepository
import com.example.makeitso.model.service.CrashlyticsService
import com.example.makeitso.model.shared_prefs.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val crashlyticsService: CrashlyticsService,
    private val taskRepository: TaskRepository,
    private val userRepository: UserRepository,
    private val sharedPrefs: SharedPrefs
) : ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(throwable) }
    }

    fun initialize() {
        viewModelScope.launch(exceptionHandler) {
            taskRepository.selectAllForUser(sharedPrefs.getCurrentUserId())
            //Should be Firestore
            //Should remove unnecessary methods from repository
        }
    }

    fun onTaskActionClick(action: String, taskId: String) {
        //Decide which action based on enum
    }

    fun onAddTaskClick(navController: NavHostController) {
        navController.navigate(EDIT_TASK_SCREEN)
    }

    private fun onEditTaskClick(navController: NavHostController, taskId: String) {
        navController.navigate(EDIT_TASK_SCREEN) //pass along the task id
    }

    private fun onDeleteTaskClick() {

    }

    private fun onFlagTaskClick() {

    }

    fun onSignOutClick(navController: NavHostController) {
        viewModelScope.launch(exceptionHandler) {
            userRepository.delete(sharedPrefs.getCurrentUserId())
            sharedPrefs.deleteCurrentUser()

            navController.navigate(LOGIN_SCREEN) {
                popUpTo(TASKS_SCREEN) { inclusive = true }
            }
        }
    }
}