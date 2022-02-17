package com.example.makeitso.screens.edit_task

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.makeitso.common.navigation.TASK_DEFAULT_ID
import com.example.makeitso.model.database.repository.TaskRepository
import com.example.makeitso.model.service.CrashlyticsService
import com.example.makeitso.model.service.FirestoreService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val crashlyticsService: CrashlyticsService,
    private val firestoreService: FirestoreService,
    private val taskRepository: TaskRepository
) : ViewModel() {
    var uiState = mutableStateOf(EditTaskUiState())
        private set

    private val currentState get() = uiState.value
    private val task get() = currentState.task

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        uiState.value = currentState.copy(hasError = true)
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(throwable) }
    }

    fun initialize(taskId: Long) {
        viewModelScope.launch(exceptionHandler) {
            if (taskId != TASK_DEFAULT_ID) {
                uiState.value = EditTaskUiState(firestoreService.getTask(taskId))
            }
        }
    }

    fun onTitleChange(newValue: String) {
        uiState.value = EditTaskUiState(task.copy(title = newValue))
    }

    fun onDescriptionChange(newValue: String) {
        uiState.value = EditTaskUiState(task.copy(description = newValue))
    }

    fun onUrlChange(newValue: String) {
        uiState.value = EditTaskUiState(task.copy(url = newValue))
    }

    fun onDateChange(newValue: Long) {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(UTC))
        calendar.timeInMillis = newValue
        val newDueDate = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).format(calendar.time)
        uiState.value = EditTaskUiState(task.copy(dueDate = newDueDate))
    }

    fun onTimeChange(hour: Int, minute: Int) {
        val newDueTime = "${hour.toClockPattern()}:${minute.toClockPattern()}"
        uiState.value = EditTaskUiState(task.copy(dueTime = newDueTime))
    }

    fun onFlagToggle(newValue: String)  {
        val newFlagOption = EditFlagOption.getBooleanValue(newValue)
        uiState.value = EditTaskUiState(task.copy(flag = newFlagOption))
    }

    fun onPriorityChange(newValue: String) {
        uiState.value = EditTaskUiState(task.copy(priority = newValue))
    }

    fun onDoneClick(navController: NavHostController) {
        viewModelScope.launch(exceptionHandler) {
            uiState.value = currentState.copy(hasError = false)

            firestoreService.saveTask(task)

            taskRepository.insert(task)
            navController.popBackStack()
        }
    }

    private fun Int.toClockPattern(): String {
        return if (this < 10) "0$this" else "$this"
    }

    companion object {
        private const val UTC = "UTC"
        private const val DATE_FORMAT = "EEE, d MMM yyyy"
    }
}
