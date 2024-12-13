package com.google.firebase.example.makeitso.ui.todoitem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.example.makeitso.R
import com.google.firebase.example.makeitso.data.model.Priority
import com.google.firebase.example.makeitso.data.model.Priority.HIGH
import com.google.firebase.example.makeitso.data.model.Priority.LOW
import com.google.firebase.example.makeitso.data.model.Priority.MEDIUM
import com.google.firebase.example.makeitso.data.model.TodoItem
import com.google.firebase.example.makeitso.data.model.isHighPriority
import com.google.firebase.example.makeitso.data.model.isLowPriority
import com.google.firebase.example.makeitso.data.model.isMediumPriority
import com.google.firebase.example.makeitso.ui.shared.AppSwitch
import com.google.firebase.example.makeitso.ui.shared.CenterTopAppBar
import com.google.firebase.example.makeitso.ui.shared.LoadingIndicator
import com.google.firebase.example.makeitso.ui.shared.StandardButton
import com.google.firebase.example.makeitso.ui.theme.DarkGrey
import com.google.firebase.example.makeitso.ui.theme.MediumYellow
import kotlinx.serialization.Serializable

@Serializable
data class TodoItemRoute(val itemId: String)

@Composable
fun TodoItemScreen(
    openHomeScreen: () -> Unit,
    showErrorSnackbar: (String) -> Unit,
    viewModel: TodoItemViewModel = hiltViewModel()
) {
    val todoItem by viewModel.todoItem.collectAsStateWithLifecycle()

    if (todoItem == null) LoadingIndicator()
    else TodoItemScreenContent(todoItem!!, openHomeScreen, showErrorSnackbar, viewModel)

    LaunchedEffect(true) {
        viewModel.loadItem()
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TodoItemScreenContent(
    todoItem: TodoItem,
    openHomeScreen: () -> Unit,
    showErrorSnackbar: (String) -> Unit,
    viewModel: TodoItemViewModel
) {
    val editableItem = remember { mutableStateOf(todoItem) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            CenterTopAppBar(
                title = stringResource(R.string.edit_todo_item),
                icon = Icons.Filled.Check,
                iconDescription = "Save Todo Item icon",
                action = {
                    viewModel.saveItem(editableItem.value, openHomeScreen, showErrorSnackbar)
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = 4.dp,
                    end = 4.dp,
                    bottom = 4.dp
                )
        ) {
            Spacer(Modifier.size(24.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                value = editableItem.value.title,
                onValueChange = { editableItem.value = editableItem.value.copy(title = it) },
                label = { Text(stringResource(R.string.title)) }
            )

            Spacer(Modifier.size(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(48f))
                    .background(MediumYellow),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(16.dp).weight(1f),
                    text = stringResource(R.string.flag)
                )

                AppSwitch(editableItem.value.flagged) {
                    editableItem.value = editableItem.value.copy(flagged = it)
                }
            }

            Spacer(Modifier.size(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(48f))
                    .background(MediumYellow)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 16.dp, bottom = 8.dp),
                    text = stringResource(R.string.priority)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PriorityChip(HIGH, editableItem.value.isHighPriority()) {
                        editableItem.value = editableItem.value.copy(
                            priority = HIGH.value
                        )
                    }

                    PriorityChip(MEDIUM, editableItem.value.isMediumPriority()) {
                        editableItem.value = editableItem.value.copy(
                            priority = MEDIUM.value
                        )
                    }

                    PriorityChip(LOW, editableItem.value.isLowPriority()) {
                        editableItem.value = editableItem.value.copy(
                            priority = LOW.value
                        )
                    }
                }
            }

            Spacer(Modifier.size(24.dp))

            StandardButton(R.string.delete_todo_item) {
                viewModel.deleteItem(todoItem, openHomeScreen)
            }
        }
    }
}

@Composable
fun PriorityChip(
    priority: Priority,
    selected: Boolean,
    onPrioritySelected: () -> Unit
) {
    FilterChip(
        onClick = {
            if (selected) return@FilterChip
            else onPrioritySelected()
        },
        label = { Text(priority.title) },
        selected = selected,
        colors = getPriorityChipColors(priority.selectedColor),
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else null
    )
}

private fun getPriorityChipColors(selectedColor: Color): SelectableChipColors {
    return SelectableChipColors(
        containerColor = MediumYellow,
        labelColor = DarkGrey,
        leadingIconColor = DarkGrey,
        trailingIconColor = DarkGrey,
        disabledContainerColor = MediumYellow,
        disabledLabelColor = DarkGrey,
        disabledLeadingIconColor = DarkGrey,
        disabledTrailingIconColor = DarkGrey,
        selectedContainerColor = selectedColor,
        disabledSelectedContainerColor = MediumYellow,
        selectedLabelColor = DarkGrey,
        selectedLeadingIconColor = DarkGrey,
        selectedTrailingIconColor = DarkGrey
    )
}
