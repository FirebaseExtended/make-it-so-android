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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.makeitso.common.composable.ActionToolbar
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText
import com.example.makeitso.theme.BrightOrange

@Composable
@ExperimentalMaterialApi
fun TasksScreen(openAddTask: () -> Unit, openEditTask: (String) -> Unit, openSettings: () -> Unit) {
    val viewModel = hiltViewModel<TasksViewModel>()

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = openAddTask,
                backgroundColor = BrightOrange,
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp)
            ) { Icon(Icons.Filled.Add, "Add") }
        }
    ) {
        ScreenContent(openEditTask, openSettings, viewModel)
    }
}

@Composable
@ExperimentalMaterialApi
private fun ScreenContent(
    openEditTask: (String) -> Unit,
    openSettings: () -> Unit,
    viewModel: TasksViewModel
) {
    val tasks = viewModel.tasks.value

    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        ActionToolbar(
            title = AppText.tasks,
            endActionIcon = AppIcon.ic_settings,
            endAction = openSettings
        )

        Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))

        LazyColumn {
            items(tasks) { taskItem ->
                TaskItem(
                    task = taskItem,
                    onCheckChange = { viewModel.onTaskCheckChange(taskItem) },
                    onActionClick = { action ->
                        viewModel.onTaskActionClick(openEditTask, taskItem, action)
                    }
                )
            }
        }
    }
}
