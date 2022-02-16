package com.example.makeitso.screens.edit_task

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.makeitso.model.Task
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
    var task = mutableStateOf(Task())
        private set

    private val currentState get() = task.value

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        //Create error state
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(throwable) }
    }

    fun initialize(taskId: Long) {
        viewModelScope.launch(exceptionHandler) {
            task.value = firestoreService.getTask(taskId)
        }
    }

    fun onTitleChange(newValue: String) {
        task.value = currentState.copy(title = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        task.value = currentState.copy(description = newValue)
    }

    fun onUrlChange(newValue: String) {
        task.value = currentState.copy(url = newValue)
    }

    fun onDateChange(newValue: Long) {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(UTC))
        calendar.timeInMillis = newValue
        val simpleFormat = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
        task.value = currentState.copy(dueDate = simpleFormat.format(calendar.time))
    }

    fun onTimeChange(hour: Int, minute: Int) {
        task.value = currentState.copy(dueTime = "$hour:$minute")
    }

    fun onFlagToggle(newValue: String)  {
        task.value = currentState.copy(flag = EditFlagOptions.getBooleanValue(newValue))
    }

    fun onPriorityChange(newValue: String) {
        task.value = currentState.copy(priority = newValue)
    }

    fun onDoneClick(navController: NavHostController) {
        viewModelScope.launch(exceptionHandler) {
            firestoreService.saveTask(currentState)

            taskRepository.insert(currentState)
            navController.popBackStack()
        }
    }

    companion object {
        private const val UTC = "UTC"
        private const val DATE_FORMAT = "EEE, d MMM yyyy"
    }
}
