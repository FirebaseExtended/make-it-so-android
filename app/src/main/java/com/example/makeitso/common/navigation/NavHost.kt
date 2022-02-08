package com.example.makeitso.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.makeitso.screens.edit_task.EditTaskScreen
import com.example.makeitso.screens.login.LoginScreen
import com.example.makeitso.screens.sign_up.SignUpScreen
import com.example.makeitso.screens.tasks.TasksScreen

const val LOGIN_SCREEN = "LoginScreen"
const val SIGN_UP_SCREEN = "SignUpScreen"
const val TASKS_SCREEN = "TasksScreen"
const val EDIT_TASK_SCREEN = "EditTaskScreen"
const val EDIT_TASK_DEFAULT_ID = "-1"

private const val TASK_ID = "taskId"
private const val TASK_ID_ARG = "?$TASK_ID={$TASK_ID}"

@Composable
fun NavHost(firstScreen: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = firstScreen) {
        composable(LOGIN_SCREEN) { LoginScreen(navController) }
        composable(SIGN_UP_SCREEN) { SignUpScreen(navController) }
        composable(TASKS_SCREEN) { TasksScreen(navController) }

        composable(
            route = "$EDIT_TASK_SCREEN$TASK_ID_ARG",
            arguments = listOf(navArgument(TASK_ID) { defaultValue = EDIT_TASK_DEFAULT_ID })
        ) {
            EditTaskScreen(navController, it.arguments?.getString(TASK_ID).orEmpty())
        }
    }
}
