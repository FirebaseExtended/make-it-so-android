/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.example.makeitso.screens.tasks

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.composable.ActionToolbar
import com.example.makeitso.common.ext.smallSpacer
import com.example.makeitso.common.ext.toolbarActions
import com.example.makeitso.model.Task
import com.example.makeitso.theme.MakeItSoTheme

@Composable
@ExperimentalMaterialApi
fun TasksScreen(
  openScreen: (String) -> Unit,
  viewModel: TasksViewModel = hiltViewModel()
) {
  val tasks = viewModel.tasks.collectAsStateWithLifecycle(emptyList())
  val options by viewModel.options

  TasksScreenContent(
    tasks = tasks.value,
    options = options,
    onAddClick = viewModel::onAddClick,
    onStatsClick = viewModel::onStatsClick,
    onSettingsClick = viewModel::onSettingsClick,
    onTaskCheckChange = viewModel::onTaskCheckChange,
    onTaskActionClick = viewModel::onTaskActionClick,
    openScreen = openScreen
  )

  LaunchedEffect(viewModel) { viewModel.loadTaskOptions() }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@ExperimentalMaterialApi
fun TasksScreenContent(
  modifier: Modifier = Modifier,
  tasks: List<Task>,
  options: List<String>,
  onAddClick: ((String) -> Unit) -> Unit,
  onStatsClick: ((String) -> Unit) -> Unit,
  onSettingsClick: ((String) -> Unit) -> Unit,
  onTaskCheckChange: (Task) -> Unit,
  onTaskActionClick: ((String) -> Unit, Task, String) -> Unit,
  openScreen: (String) -> Unit
) {
  Scaffold(
    floatingActionButton = {
      FloatingActionButton(
        onClick = { onAddClick(openScreen) },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        modifier = modifier.padding(16.dp)
      ) {
        Icon(Icons.Filled.Add, "Add")
      }
    }
  ) {
    Column(modifier = Modifier
      .fillMaxWidth()
      .fillMaxHeight()) {
      ActionToolbar(
        title = AppText.tasks,
        modifier = Modifier.toolbarActions(),
        primaryActionIcon = AppIcon.ic_stats,
        primaryAction = { onStatsClick(openScreen) },
        secondaryActionIcon = AppIcon.ic_settings,
        secondaryAction = { onSettingsClick(openScreen) }
      )

      Spacer(modifier = Modifier.smallSpacer())

      LazyColumn {
        items(tasks, key = { it.id }) { taskItem ->
          TaskItem(
            task = taskItem,
            options = options,
            onCheckChange = { onTaskCheckChange(taskItem) },
            onActionClick = { action -> onTaskActionClick(openScreen, taskItem, action) }
          )
        }
      }
    }
  }
}

@Preview(showBackground = true)
@ExperimentalMaterialApi
@Composable
fun TasksScreenPreview() {
  val task = Task(
    title = "Task title",
    flag = true,
    completed = true
  )

  val options = TaskActionOption.getOptions(hasEditOption = true)

  MakeItSoTheme {
    TasksScreenContent(
      tasks = listOf(task),
      options = options,
      onAddClick = { },
      onStatsClick = { },
      onSettingsClick = { },
      onTaskCheckChange = { },
      onTaskActionClick = { _, _, _ -> },
      openScreen = { }
    )
  }
}
