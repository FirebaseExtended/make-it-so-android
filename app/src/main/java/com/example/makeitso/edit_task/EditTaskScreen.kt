package com.example.makeitso.edit_task

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.makeitso.R.string as Text
import com.example.makeitso.common.ToolBar

//This method should be private and part of a fragment in the future
@Preview
@Composable
fun EditTaskScreen() {
    ToolBar(Text.edit_task)
}