package com.example.makeitso.screens.edit_task

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makeitso.TASK_DEFAULT_ID
import com.example.makeitso.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.makeitso.common.ext.idFromParameter
import com.example.makeitso.common.snackbar.SnackbarManager
import com.example.makeitso.R.string as AppText
import com.example.makeitso.model.Task
import com.example.makeitso.model.service.AccountService
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
    private val accountService: AccountService
) : ViewModel() {
    var task = mutableStateOf(Task())
        private set

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        SnackbarManager.showMessage(AppText.generic_error)
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(throwable) }
    }

    fun initialize(taskId: String) {
        viewModelScope.launch(exceptionHandler) {
            if (taskId != TASK_DEFAULT_ID) {
                firestoreService.getTask(taskId.idFromParameter(), ::onError) {
                    task.value = it
                }
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

    fun onDoneClick(popUpScreen: () -> Unit) {
        viewModelScope.launch(exceptionHandler) {
            val editedTask = task.value.copy(userId = accountService.getUserId())

            firestoreService.saveTask(editedTask) { error ->
                if (error == null) popUpScreen() else onError(error)
            }
        }
    }

    private fun Int.toClockPattern(): String {
        return if (this < 10) "0$this" else "$this"
    }

    private fun onError(error: Throwable?) {
        SnackbarManager.showMessage(error.toSnackbarMessage())
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(error) }
    }

    companion object {
        private const val UTC = "UTC"
        private const val DATE_FORMAT = "EEE, d MMM yyyy"
    }
}
