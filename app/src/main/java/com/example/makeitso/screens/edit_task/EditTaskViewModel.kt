package com.example.makeitso.screens.edit_task

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.makeitso.model.Task
import com.example.makeitso.model.database.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    fun onDoneClick(navController: NavHostController) {
        //taskRepository.insert(task)
        navController.popBackStack()
    }

    fun getTask(taskId: Long): Task? {
        //return taskRepository.select(taskId)
        return null
    }

    fun onFlagToggle(newValue: String)  = Unit

    fun onPriorityChange(newValue: String) = Unit

    fun onDateChange(newValue: Long) {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(UTC))
        calendar.timeInMillis = newValue
        val simpleFormat = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
        simpleFormat.format(calendar.time)
    }

    fun onTimeChange(hour: Int, minute: Int) {
        val newValue = "$hour:$minute"
    }

    companion object {
        private const val UTC = "UTC"
        private const val DATE_FORMAT = "EEE, d MMM yyyy"
    }
}
