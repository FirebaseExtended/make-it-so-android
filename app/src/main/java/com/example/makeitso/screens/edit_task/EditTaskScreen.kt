package com.example.makeitso.screens.edit_task

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.makeitso.common.*
import com.example.makeitso.common.BasicField
import com.example.makeitso.model.Task
import com.example.makeitso.model.Task.Companion.hasDueDate
import com.example.makeitso.model.Task.Companion.hasDueTime
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText

@Composable
fun EditTaskScreen(task: Task) {
    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        ToolBar(
            title = AppText.edit_task,
            endActionIcon = AppIcon.ic_check,
            endAction = {} //Call ViewModel
        )

        Spacer(modifier = Modifier.fillMaxWidth().padding(8.dp))

        BasicField(AppText.title, initialState = task.title)
        BasicField(AppText.description, initialState = task.description)
        BasicField(AppText.url, initialState = task.url)

        Spacer(modifier = Modifier.fillMaxWidth().padding(16.dp))

        CardSwitch(AppText.date, AppIcon.ic_calendar, task.hasDueDate(), task.dueDate)
        CardSwitch(AppText.time, AppIcon.ic_clock, task.hasDueTime(), task.dueTime)
        CardSwitch(AppText.flag, AppIcon.ic_flag, task.flag)

        Spacer(modifier = Modifier.fillMaxWidth().padding(16.dp))

        CardSelector(AppText.priority, task.priority.title)
    }
}
