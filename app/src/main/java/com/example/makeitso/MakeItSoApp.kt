package com.example.makeitso

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.makeitso.screens.edit_task.EditTaskScreen
import com.example.makeitso.screens.login.LoginScreen
import com.example.makeitso.screens.settings.SettingsScreen
import com.example.makeitso.screens.sign_up.SignUpScreen
import com.example.makeitso.screens.splash.SplashScreen
import com.example.makeitso.screens.tasks.TasksScreen
import com.example.makeitso.theme.MakeItSoTheme

@Composable
@ExperimentalMaterialApi
fun MakeItSoApp() {
    MakeItSoTheme {
        Surface(color = MaterialTheme.colors.background) {

            val appState = rememberAppState()

            Scaffold(scaffoldState = appState.scaffoldState) { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = SPLASH_SCREEN,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) { makeItSoGraph(appState) }
            }
        }
    }
}

@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController()
) = remember(scaffoldState, navController) {
    MakeItSoAppState(scaffoldState, navController)
}

@ExperimentalMaterialApi
fun NavGraphBuilder.makeItSoGraph(appState: MakeItSoAppState) {
    composable(SPLASH_SCREEN) {
        SplashScreen(openTasks = { appState.popUpAndNavigate(TASKS_SCREEN, SPLASH_SCREEN) })
    }

    composable(SETTINGS_SCREEN) {
        SettingsScreen(
            signOut = { appState.clearAndNavigate(SPLASH_SCREEN) },
            openLogin = { appState.navigate(LOGIN_SCREEN) },
            popUpScreen = { appState.popUp() }
        )
    }

    composable(LOGIN_SCREEN) {
        LoginScreen(
            openSignUp = { appState.popUpAndNavigate(SIGN_UP_SCREEN, LOGIN_SCREEN) },
            popUpScreen = { appState.popUp() }
        )
    }

    composable(SIGN_UP_SCREEN) {
        SignUpScreen(popUpScreen = { appState.popUp() })
    }

    composable(TASKS_SCREEN) {
        TasksScreen(
            openAddTask = { appState.navigate(EDIT_TASK_SCREEN) },
            openEditTask = { id -> appState.navigate("$EDIT_TASK_SCREEN?$TASK_ID={$id}")},
            openSettings = { appState.navigate(SETTINGS_SCREEN) }
        )
    }

    composable(
        route = "$EDIT_TASK_SCREEN$TASK_ID_ARG",
        arguments = listOf(navArgument(TASK_ID) { defaultValue = TASK_DEFAULT_ID })
    ) {
        EditTaskScreen(
            popUpScreen = { appState.popUp() },
            taskId = it.arguments?.getString(TASK_ID) ?: TASK_DEFAULT_ID
        )
    }
}