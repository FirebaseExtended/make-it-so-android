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

package com.example.makeitso.screens.edit_task

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.composable.*
import com.example.makeitso.common.ext.card
import com.example.makeitso.common.ext.fieldModifier
import com.example.makeitso.common.ext.spacer
import com.example.makeitso.common.ext.toolbarActions
import com.example.makeitso.model.Priority
import com.example.makeitso.model.Task
import com.example.makeitso.theme.MakeItSoTheme
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

@Composable
@ExperimentalMaterialApi
fun EditTaskScreen(
  popUpScreen: () -> Unit,
  viewModel: EditTaskViewModel = hiltViewModel()
) {
  val task by viewModel.task
  val activity = LocalContext.current as AppCompatActivity

  EditTaskScreenContent(
    task = task,
    onDoneClick = { viewModel.onDoneClick(popUpScreen) },
    onTitleChange = viewModel::onTitleChange,
    onDescriptionChange = viewModel::onDescriptionChange,
    onUrlChange = viewModel::onUrlChange,
    onDateChange = viewModel::onDateChange,
    onTimeChange = viewModel::onTimeChange,
    onPriorityChange = viewModel::onPriorityChange,
    onFlagToggle = viewModel::onFlagToggle,
    activity = activity
  )
}

@Composable
@ExperimentalMaterialApi
fun EditTaskScreenContent(
  modifier: Modifier = Modifier,
  task: Task,
  onDoneClick: () -> Unit,
  onTitleChange: (String) -> Unit,
  onDescriptionChange: (String) -> Unit,
  onUrlChange: (String) -> Unit,
  onDateChange: (Long) -> Unit,
  onTimeChange: (Int, Int) -> Unit,
  onPriorityChange: (String) -> Unit,
  onFlagToggle: (String) -> Unit,
  activity: AppCompatActivity?
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .fillMaxHeight()
      .verticalScroll(rememberScrollState()),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    ActionToolbar(
      title = AppText.edit_task,
      modifier = Modifier.toolbarActions(),
      primaryActionIcon = AppIcon.ic_check,
      primaryAction = { onDoneClick() }
    )

    Spacer(modifier = Modifier.spacer())

    val fieldModifier = Modifier.fieldModifier()
    BasicField(AppText.title, task.title, onTitleChange, fieldModifier)
    BasicField(AppText.description, task.description, onDescriptionChange, fieldModifier)
    BasicField(AppText.url, task.url, onUrlChange, fieldModifier)

    Spacer(modifier = Modifier.spacer())
    CardEditors(task, onDateChange, onTimeChange, activity)
    CardSelectors(task, onPriorityChange, onFlagToggle)

    Spacer(modifier = Modifier.spacer())
  }
}

@ExperimentalMaterialApi
@Composable
private fun CardEditors(
  task: Task,
  onDateChange: (Long) -> Unit,
  onTimeChange: (Int, Int) -> Unit,
  activity: AppCompatActivity?
) {
  RegularCardEditor(AppText.date, AppIcon.ic_calendar, task.dueDate, Modifier.card()) {
    showDatePicker(activity, onDateChange)
  }

  RegularCardEditor(AppText.time, AppIcon.ic_clock, task.dueTime, Modifier.card()) {
    showTimePicker(activity, onTimeChange)
  }
}

@Composable
@ExperimentalMaterialApi
private fun CardSelectors(
  task: Task,
  onPriorityChange: (String) -> Unit,
  onFlagToggle: (String) -> Unit
) {
  val prioritySelection = Priority.getByName(task.priority).name
  CardSelector(AppText.priority, Priority.getOptions(), prioritySelection, Modifier.card()) {
    newValue ->
    onPriorityChange(newValue)
  }

  val flagSelection = EditFlagOption.getByCheckedState(task.flag).name
  CardSelector(AppText.flag, EditFlagOption.getOptions(), flagSelection, Modifier.card()) { newValue
    ->
    onFlagToggle(newValue)
  }
}

private fun showDatePicker(activity: AppCompatActivity?, onDateChange: (Long) -> Unit) {
  val picker = MaterialDatePicker.Builder.datePicker().build()

  activity?.let {
    picker.show(it.supportFragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener { timeInMillis -> onDateChange(timeInMillis) }
  }
}

private fun showTimePicker(activity: AppCompatActivity?, onTimeChange: (Int, Int) -> Unit) {
  val picker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()

  activity?.let {
    picker.show(it.supportFragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener { onTimeChange(picker.hour, picker.minute) }
  }
}

@Preview(showBackground = true)
@ExperimentalMaterialApi
@Composable
fun EditTaskScreenPreview() {
  val task = Task(
    title = "Task title",
    description = "Task description",
    flag = true
  )

  MakeItSoTheme {
    EditTaskScreenContent(
      task = task,
      onDoneClick = { },
      onTitleChange = { },
      onDescriptionChange = { },
      onUrlChange = { },
      onDateChange = { },
      onTimeChange = { _, _ -> },
      onPriorityChange = { },
      onFlagToggle = { },
      activity = null
    )
  }
}
