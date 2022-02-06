package com.example.makeitso.screens.edit_task

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.makeitso.common.*
import com.example.makeitso.common.BasicField
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText

@Composable
fun EditTaskScreen() { //Receive Task
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        ToolBar(
            title = AppText.edit_task,
            endActionIcon = AppIcon.ic_check,
            endAction = {} //Call ViewModel
        )

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(8.dp))

        BasicField(AppText.title) //Logic to use Task data
        BasicField(AppText.description)
        BasicField(AppText.url)

        FlagButton()
    }
}

@Composable
private fun FlagButton() {

}
