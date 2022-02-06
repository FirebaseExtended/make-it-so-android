package com.example.makeitso.screens.tasks

import androidx.compose.runtime.Composable
import com.example.makeitso.R
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.ToolBar

//This method should be private and part of a fragment in the future

@Composable
fun TasksScreen() {
    ToolBar(
        AppText.tasks,
        endActionIcon = R.drawable.ic_exit,
        endAction = {} //Call ViewModel
    )
}
