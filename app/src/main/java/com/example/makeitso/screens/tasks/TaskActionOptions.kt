package com.example.makeitso.screens.tasks

enum class TaskActionOptions(val title: String) {
    EditTask("Edit task"),
    ToggleFlag("Toggle flag"),
    DeleteTask("Delete task");

    companion object {
       fun getOptions(): List<String> {
            val options = mutableListOf<String>()
            values().forEach { taskAction -> options.add(taskAction.title) }
            return options
        }
    }
}
