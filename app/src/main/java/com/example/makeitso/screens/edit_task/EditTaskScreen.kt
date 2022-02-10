package com.example.makeitso.screens.edit_task

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.makeitso.common.composable.*
import com.example.makeitso.common.ext.hasDueDate
import com.example.makeitso.common.ext.hasDueTime
import com.example.makeitso.model.Priority
import com.example.makeitso.model.Task
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText

@Composable
@ExperimentalMaterialApi
fun EditTaskScreen(navController: NavHostController, taskId: Long) {
    val viewModel = hiltViewModel<EditTaskViewModel>()
    val task: Task? = viewModel.getTask(taskId) //Listen to ViewModel state instead

    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        ActionToolbar(
            title = AppText.edit_task,
            endActionIcon = AppIcon.ic_check,
            endAction = { viewModel.onDoneClick(navController) }
        )

        Spacer(modifier = Modifier.fillMaxWidth().padding(8.dp))

        BasicField(AppText.title, initialState = task?.title.orEmpty())
        BasicField(AppText.description, initialState = task?.description.orEmpty())
        BasicField(AppText.url, initialState = task?.url.orEmpty())

        Spacer(modifier = Modifier.fillMaxWidth().padding(16.dp))

        CardSwitch(AppText.date, AppIcon.ic_calendar, task.hasDueDate(), task?.dueDate.orEmpty())
        CardSwitch(AppText.time, AppIcon.ic_clock, task.hasDueTime(), task?.dueTime.orEmpty())
        CardSwitch(AppText.flag, AppIcon.ic_flag, task?.flag ?: false)

        Spacer(modifier = Modifier.fillMaxWidth().padding(16.dp))

        val options = Priority.getAllNames()
        val selection = Priority.getByName(task?.priority).name
        CardSelector(AppText.priority, options, selection)
    }
}
