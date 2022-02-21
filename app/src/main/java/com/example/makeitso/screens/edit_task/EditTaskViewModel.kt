package com.example.makeitso.screens.edit_task

import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.navigation.TASK_DEFAULT_ID
import com.example.makeitso.common.navigation.idFromParameter
import com.example.makeitso.model.Task
import com.example.makeitso.model.database.repository.TaskRepository
import com.example.makeitso.model.service.CrashlyticsService
import com.example.makeitso.model.service.FirestoreService
import com.example.makeitso.model.shared_prefs.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val crashlyticsService: CrashlyticsService,
    private val firestoreService: FirestoreService,
    private val taskRepository: TaskRepository,
    private val sharedPrefs: SharedPrefs
) : ViewModel() {
    var task = mutableStateOf(Task())
        private set

    val snackbarChannel = Channel<@StringRes Int>(Channel.CONFLATED)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        snackbarChannel.trySend(AppText.generic_error)
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(throwable) }
    }

    fun initialize(taskId: String) {
        viewModelScope.launch(exceptionHandler) {
            if (taskId != TASK_DEFAULT_ID) {
                val id = taskId.idFromParameter().toLong()
                task.value = firestoreService.getTask(id)
            }
        }
    }

    fun onTitleChange(newValue: String) {
        task.value = task.value.copy(title = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        task.value = task.value.copy(description = newValue)
    }

    fun onUrlChange(newValue: String) {
        task.value = task.value.copy(url = newValue)
    }

    fun onDateChange(newValue: Long) {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(UTC))
        calendar.timeInMillis = newValue
        val newDueDate = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).format(calendar.time)
        task.value = task.value.copy(dueDate = newDueDate)
    }

    fun onTimeChange(hour: Int, minute: Int) {
        val newDueTime = "${hour.toClockPattern()}:${minute.toClockPattern()}"
        task.value = task.value.copy(dueTime = newDueTime)
    }

    fun onFlagToggle(newValue: String)  {
        val newFlagOption = EditFlagOption.getBooleanValue(newValue)
        task.value = task.value.copy(flag = newFlagOption)
    }

    fun onPriorityChange(newValue: String) {
        task.value = task.value.copy(priority = newValue)
    }

    fun onDoneClick(navController: NavHostController) {
        viewModelScope.launch(exceptionHandler) {
            val editedTask = task.value.copy(userId = sharedPrefs.getCurrentUserId())

            firestoreService.saveTask(editedTask)
            taskRepository.insert(editedTask)

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
