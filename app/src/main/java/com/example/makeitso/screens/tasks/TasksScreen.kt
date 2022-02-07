package com.example.makeitso.screens.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.ToolBar
import com.example.makeitso.theme.BrightOrange

@Composable
fun TasksScreen() {
    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        ToolBar(
            title = AppText.tasks,
            endActionIcon = AppIcon.ic_exit,
            endAction = {} //Call ViewModel
        )

        Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))

        Scaffold(floatingActionButton = {
            FloatingActionButton(
                onClick = { }, //Call ViewModel
                backgroundColor = BrightOrange,
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp)
            ){
                Icon(Icons.Filled.Add,"Add")
            }
        }) {
//            LazyColumn {
//                items(state.tasks) { task ->
//                    TaskItem(
//                        task = task,
//                        onChecked = { /* Call ViewModel */ },
//                        onEdit = { /* Call ViewModel */ }
//                    )
//                }
//            }
        }
    }
}
