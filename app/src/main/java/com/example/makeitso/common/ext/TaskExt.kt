package com.example.makeitso.common.ext

import com.example.makeitso.model.Task

fun Task?.hasDueDate(): Boolean {
    return this?.dueDate.orEmpty().isNotBlank()
}

fun Task?.hasDueTime(): Boolean {
    return this?.dueTime.orEmpty().isNotBlank()
}