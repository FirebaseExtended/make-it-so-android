package com.example.makeitso.screens.edit_task

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.makeitso.common.composable.*
import com.example.makeitso.model.Priority
import com.example.makeitso.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText

@Composable
@ExperimentalMaterialApi
fun EditTaskScreen(navController: NavHostController, taskId: Long) {
    val viewModel = hiltViewModel<EditTaskViewModel>()
    val uiState = viewModel.uiState.value

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActionToolbar(
            title = AppText.edit_task,
            endActionIcon = AppIcon.ic_check,
            endAction = { viewModel.onDoneClick(navController) }
        )

        Spacer(modifier = Modifier.fillMaxWidth().padding(12.dp))
        BasicFields(uiState.task, viewModel)

        Spacer(modifier = Modifier.fillMaxWidth().padding(12.dp))
        CardEditors(uiState.task, viewModel)
        CardSelectors(uiState.task, viewModel)

        Spacer(modifier = Modifier.fillMaxWidth().padding(12.dp))

        if (uiState.hasError) {
            BasicText(text = AppText.generic_error, color = Color.Red)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initialize(taskId)
    }
}

@Composable
private fun BasicFields(task: Task, viewModel: EditTaskViewModel) {
    BasicField(AppText.title, task.title, viewModel::onTitleChange)
    BasicField(AppText.description, task.description, viewModel::onDescriptionChange)
    BasicField(AppText.url, task.url, viewModel::onUrlChange)
}

@Composable
private fun CardEditors(task: Task, viewModel: EditTaskViewModel) {
    val activity = LocalContext.current as AppCompatActivity

    CardEditor(AppText.date, AppIcon.ic_calendar, task.dueDate) {
        showDatePicker(activity, viewModel)
    }

    CardEditor(AppText.time, AppIcon.ic_clock, task.dueTime) {
        showTimePicker(activity, viewModel)
    }
}

@Composable
@ExperimentalMaterialApi
private fun CardSelectors(task: Task, viewModel: EditTaskViewModel) {
    val prioritySelection = Priority.getByName(task.priority).name
    CardSelector(AppText.priority, Priority.getOptions(), prioritySelection) { newValue ->
        viewModel.onPriorityChange(newValue)
    }

    val flagSelection = EditFlagOptions.getByCheckedState(task.flag).name
    CardSelector(AppText.flag, EditFlagOptions.getOptions(), flagSelection) { newValue ->
        viewModel.onFlagToggle(newValue)
    }
}

private fun showDatePicker(activity: AppCompatActivity?, viewModel: EditTaskViewModel) {
    val picker = MaterialDatePicker.Builder.datePicker().build()

    activity?.let {
        picker.show(it.supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener { timeInMillis ->
            viewModel.onDateChange(timeInMillis)
        }
    }
}

private fun showTimePicker(activity: AppCompatActivity?, viewModel: EditTaskViewModel) {
    val picker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()

    activity?.let {
        picker.show(it.supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener {
            viewModel.onTimeChange(picker.hour, picker.minute)
        }
    }
}
