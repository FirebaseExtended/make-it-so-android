package com.example.makeitso.screens.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.makeitso.common.composable.ActionToolbar
import com.example.makeitso.common.ext.smallSpacer
import com.example.makeitso.common.ext.toolbarActions
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText

@Composable
@ExperimentalMaterialApi
fun TasksScreen(openAddTask: () -> Unit, openEditTask: (String) -> Unit, openSettings: () -> Unit) {
    val viewModel = hiltViewModel<TasksViewModel>()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = openAddTask,
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(16.dp)
            ) { Icon(Icons.Filled.Add, "Add") }
        }
    ) {
        ScreenContent(openEditTask, openSettings, viewModel)
    }

    DisposableEffect(viewModel) {
        viewModel.addListener()
        onDispose { viewModel.removeListener() }
    }
}

@Composable
@ExperimentalMaterialApi
private fun ScreenContent(
    openEditTask: (String) -> Unit,
    openSettings: () -> Unit,
    viewModel: TasksViewModel
) {
    val tasks = viewModel.tasks

    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        ActionToolbar(
            title = AppText.tasks,
            modifier = Modifier.toolbarActions(),
            endActionIcon = AppIcon.ic_settings,
            endAction = openSettings
        )

        Spacer(modifier = Modifier.smallSpacer())

        LazyColumn {
            items(tasks, key = { it.id }) { taskItem ->
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
