package com.example.makeitso.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.composable.*
import com.example.makeitso.common.error.ErrorMessage.Companion.toMessage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun SettingsScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<SettingsViewModel>()

    val context = LocalContext.current
    val snackbarChannel = remember { viewModel.snackbarChannel }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.initialize()

        snackbarChannel.receiveAsFlow().collect { errorMessage ->
            snackbarHostState.showSnackbar(errorMessage.toMessage(context))
        }
    }

    Scaffold(scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)) {
        ScreenContent(navController, viewModel)
    }
}

@Composable
private fun ScreenContent(navController: NavHostController, viewModel: SettingsViewModel) {
    var showWarningDialog by remember { mutableStateOf(false) }
    val uiState = viewModel.uiState.value

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicToolbar(AppText.settings) {
            viewModel.onBackClick(navController)
        }

        Spacer(modifier = Modifier.fillMaxWidth().padding(12.dp))

        CardEditor(AppText.link_with_account, AppIcon.ic_account, "") {
            viewModel.onLinkAccountClick(navController)
        }

        CardEditor(AppText.sign_out, AppIcon.ic_exit, "") {
            if (uiState.isAnonymousAccount) showWarningDialog = true
            else viewModel.onSignOutClick(navController)
        }

        if(showWarningDialog) {
            AlertDialog(
                title = { Text(stringResource(AppText.warning)) },
                text = { Text(stringResource(AppText.anonymous_warning)) },
                dismissButton = { DialogButton(AppText.cancel) { showWarningDialog = false } },
                confirmButton = {
                    DialogButton(AppText.sign_out) {
                        viewModel.onSignOutClick(navController)
                        showWarningDialog = false
                    }
                },
                onDismissRequest = { showWarningDialog = false }
            )
        }
    }
}
