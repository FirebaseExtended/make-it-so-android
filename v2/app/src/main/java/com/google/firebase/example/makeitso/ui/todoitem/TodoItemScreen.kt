package com.google.firebase.example.makeitso.ui.todoitem

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.example.makeitso.R
import com.google.firebase.example.makeitso.data.model.TodoItem
import com.google.firebase.example.makeitso.ui.shared.CenterTopAppBar
import com.google.firebase.example.makeitso.ui.shared.LoadingIndicator
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
                title = stringResource(R.string.edit_your_item),
                icon = Icons.Filled.Check,
                iconDescription = "Save Todo Item icon",
                action = {
                    viewModel.saveItem(editableItem.value, openHomeScreen, showErrorSnackbar)
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = 4.dp,
                    end = 4.dp,
                    bottom = 4.dp
                )
        ) {
            val (title, priority, flag, deleteButton) = createRefs()

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(horizontal = 24.dp),
                value = editableItem.value.title,
                onValueChange = { editableItem.value = editableItem.value.copy(title = it) },
                label = { Text(stringResource(R.string.title)) }
            )
        }
    }
}