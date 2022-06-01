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

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.makeitso.common.composable.ActionToolbar
import com.example.makeitso.common.ext.smallSpacer
import com.example.makeitso.common.ext.toolbarActions
import com.example.makeitso.model.Task
import com.example.makeitso.theme.MakeItSoTheme
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText

@Composable
@ExperimentalMaterialApi
fun TasksScreen(
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    tasks: List<Task>,
    onTaskCheckChange: (Task) -> Unit,
    onSettingsClick: ((String) -> Unit) -> Unit,
    onAddClick: ((String) -> Unit) -> Unit,
    onTaskActionClick: ((String) -> Unit, Task, String) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddClick(openScreen) },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                modifier = modifier.padding(16.dp)
            ) { Icon(Icons.Filled.Add, "Add") }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            ActionToolbar(
                title = AppText.tasks,
                modifier = Modifier.toolbarActions(),
                endActionIcon = AppIcon.ic_settings,
                endAction = { onSettingsClick(openScreen) }
            )

            Spacer(modifier = Modifier.smallSpacer())

            LazyColumn {
                items(tasks, key = { it.id }) { taskItem ->
                    TaskItem(
                        task = taskItem,
                        onCheckChange = { onTaskCheckChange(taskItem) },
                        onActionClick = { action ->
                            onTaskActionClick(openScreen, taskItem, action)
                        }
                    )
                }
            }
        }
    }
}

@Composable
@ExperimentalMaterialApi
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
fun TaskScreenPreview() {
    MakeItSoTheme {
        val dummyTasks = listOf(
            Task(title = "Clean up the house"),
            Task(title = "Groceries"),
            Task(title = "Prepare dinner", completed = true),
            Task(title = "Watch Brazil vs Argentina football game", dueTime = "Today at 10PM")
        )

        TasksScreen(
            openScreen = {},
            tasks = dummyTasks,
            onTaskCheckChange = {},
            onSettingsClick = {},
            onAddClick = {},
            onTaskActionClick = { _, _, _ -> }
        )
    }
}