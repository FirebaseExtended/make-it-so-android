package com.google.firebase.example.makeitso.ui.todolist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.example.makeitso.R
import com.google.firebase.example.makeitso.data.model.TodoItem
import com.google.firebase.example.makeitso.ui.shared.CenterTopAppBar
import com.google.firebase.example.makeitso.ui.shared.LoadingIndicator
import com.google.firebase.example.makeitso.ui.theme.DarkBlue
import kotlinx.serialization.Serializable

//@Serializable
//data class TodoListRoute(val listId: String) //TODO: use data class instead of object

@Serializable
object TodoListRoute

@Composable
fun TodoListScreen(
    openSignInScreen: () -> Unit,
    viewModel: TodoListViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    if(uiState.value is TodoListUiState.Loading) {
        LoadingIndicator()
    } else {
        TodoListScreenContent(openSignInScreen = openSignInScreen)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TodoListScreenContent(
    openSignInScreen: () -> Unit,
    viewModel: TodoListViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            CenterTopAppBar(
                title = stringResource(R.string.app_name), //TODO: Change to todo item name
                icon = Icons.Filled.AccountCircle,
                iconDescription = "Sign In screen icon",
                action = openSignInScreen,
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
            val (list, fab) = createRefs()
            val listState = rememberLazyListState()

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(list) {
                        top.linkTo(parent.top)
                        bottom.linkTo(fab.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    },
                contentPadding = PaddingValues(16.dp)
            ) {
                items(listOf(TodoItem(title = "1"), TodoItem(title = "2", completed = true))) { todoItem ->
                    //TODO: Use uiState

                    TodoItem(todoItem = todoItem)
                }
            }

            ExtendedFloatingActionButton(
                modifier = Modifier
                    .constrainAs(fab) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(horizontal = 16.dp),
                containerColor = DarkBlue,
                contentColor = Color.White,
                icon = { Icon(Icons.Filled.Add, "Create list button") },
                text = { Text(text = stringResource(R.string.create_todo_item)) },
                onClick = { }
            )
        }
    }
}

@Composable
fun TodoItem(todoItem: TodoItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = todoItem.completed,
            onCheckedChange = { },
            colors = CheckboxDefaults.colors(checkedColor = DarkBlue)
        )

        Text(text = todoItem.title)
    }
}
