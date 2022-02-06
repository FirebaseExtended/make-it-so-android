package com.example.makeitso.screens.edit_task

import androidx.compose.runtime.Composable
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.ToolBar

@Composable
fun EditTaskScreen() {
    ToolBar(
        AppText.edit_task,
        endActionIcon = AppIcon.ic_check,
        endAction = {} //Call ViewModel
    )
}