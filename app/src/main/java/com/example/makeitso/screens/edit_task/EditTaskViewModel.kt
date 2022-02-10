package com.example.makeitso.screens.edit_task

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.makeitso.model.Task
import com.example.makeitso.model.database.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
}