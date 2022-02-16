package com.example.makeitso.common.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.makeitso.screens.edit_task.EditTaskScreen
import com.example.makeitso.screens.login.LoginScreen
import com.example.makeitso.screens.sign_up.SignUpScreen
import com.example.makeitso.screens.splash.SplashScreen
import com.example.makeitso.screens.tasks.TasksScreen

const val SPLASH_SCREEN = "SplashScreen"
const val LOGIN_SCREEN = "LoginScreen"
const val SIGN_UP_SCREEN = "SignUpScreen"
const val TASKS_SCREEN = "TasksScreen"
const val EDIT_TASK_SCREEN = "EditTaskScreen"
const val TASK_DEFAULT_ID = -1L

private const val TASK_ID = "taskId"
private const val TASK_ID_ARG = "?$TASK_ID={$TASK_ID}"

@Composable
@ExperimentalMaterialApi
fun NavHost(firstScreen: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = firstScreen) {
        composable(SPLASH_SCREEN) { SplashScreen(navController) }
        composable(LOGIN_SCREEN) { LoginScreen(navController) }
        composable(SIGN_UP_SCREEN) { SignUpScreen(navController) }
        composable(TASKS_SCREEN) { TasksScreen(navController) }

        composable(
            route = "$EDIT_TASK_SCREEN$TASK_ID_ARG",
            arguments = listOf(navArgument(TASK_ID) { defaultValue = TASK_DEFAULT_ID })
        ) {
            EditTaskScreen(navController, it.arguments?.getLong(TASK_ID) ?: TASK_DEFAULT_ID)
        }
    }
}
