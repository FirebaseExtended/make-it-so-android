package com.example.makeitso.screens.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.makeitso.common.composable.ActionToolbar
import com.example.makeitso.common.error.ErrorMessage.Companion.toMessage
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText
import com.example.makeitso.theme.BrightOrange
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
@ExperimentalMaterialApi
fun TasksScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<TasksViewModel>()

    val context = LocalContext.current
    val snackbarChannel = remember { viewModel.snackbarChannel }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.initialize()

        snackbarChannel.receiveAsFlow().collect { errorMessage ->
            snackbarHostState.showSnackbar(errorMessage.toMessage(context))
        }
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onAddTaskClick(navController) },
                backgroundColor = BrightOrange,
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp)
            ) { Icon(Icons.Filled.Add, "Add") }
        }
    ) {
        ScreenContent(navController, viewModel)
    }
}

@Composable
@ExperimentalMaterialApi
private fun ScreenContent(navController: NavHostController, viewModel: TasksViewModel) {
    val tasks = viewModel.tasks.value

    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        ActionToolbar(
            title = AppText.tasks,
            endActionIcon = AppIcon.ic_exit,
            endAction = { viewModel.onSignOutClick(navController) }
        )

        Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))

        LazyColumn {
            items(tasks) { taskItem ->
                TaskItem(
                    task = taskItem,
                    onCheckChange = { viewModel.onTaskCheckChange(taskItem) },
                    onActionClick = { action ->
                        viewModel.onTaskActionClick(taskItem, action, navController)
                    }
                )
            }
        }
    }
}
