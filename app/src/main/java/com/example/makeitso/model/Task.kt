package com.example.makeitso.model

data class Task(
    val id: String,
    val title: String,
    val priority: Priority,
    val dueDate: String,
    val dueTime: String,
    val description: String,
    val url: String,
    val flag: Boolean,
    val completed: Boolean
) {
    companion object {
        fun Task.hasDueDate(): Boolean {
            return dueDate.isNotBlank()
        }

        fun Task.hasDueTime(): Boolean {
            return dueTime.isNotBlank()
        }
    }
}
