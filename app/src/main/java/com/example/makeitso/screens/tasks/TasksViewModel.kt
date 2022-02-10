package com.example.makeitso.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.makeitso.common.navigation.EDIT_TASK_SCREEN
import com.example.makeitso.common.navigation.LOGIN_SCREEN
import com.example.makeitso.common.navigation.TASKS_SCREEN
import com.example.makeitso.model.database.repository.TaskRepository
import com.example.makeitso.model.database.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    fun onSignOutClick(navController: NavHostController) {
        //clear database and shared preferences
        navController.navigate(LOGIN_SCREEN) {
            popUpTo(TASKS_SCREEN) { inclusive = true }
        }
    }

    fun onEditTaskClick(navController: NavHostController) {
        //Pass along the id
        navController.navigate(EDIT_TASK_SCREEN)
    }

    fun onDeleteTaskClick() {

    }

    fun onFlagTaskClick() {

    }
}