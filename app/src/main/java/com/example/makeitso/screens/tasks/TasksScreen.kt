package com.example.makeitso.screens.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.ToolBar
import com.example.makeitso.model.Task
import com.example.makeitso.theme.BrightOrange

@Composable
fun TasksScreen() {
    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        ToolBar(
            AppText.tasks,
            endActionIcon = AppIcon.ic_exit,
            endAction = {} //Call ViewModel
        )

        //LazyList
    }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(
            onClick = { }, //Call ViewModel
            backgroundColor = BrightOrange,
            contentColor = Color.White,
            modifier = Modifier.padding(16.dp)
        ){
            Icon(Icons.Filled.Add,"Add")
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onChecked: (Boolean) -> Unit,
    onEdit: (Task) -> Unit,
) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Checkbox(
                checked = task.completed,
                onCheckedChange = { onChecked(it) },
                modifier = Modifier.padding(8.dp, 0.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(text = task.title, style = MaterialTheme.typography.subtitle2)
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(text = task.description, fontSize = 12.sp)
                }
            }

            if (task.flag) {
                Icon(painter = painterResource(AppIcon.ic_flag), contentDescription = "Flag")
            }

            IconButton(onClick = { onEdit(task) }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
            }
        }
    }
}
