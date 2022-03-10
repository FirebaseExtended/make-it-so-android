package com.example.makeitso.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.composable.*

@Composable
fun SettingsScreen(
    restartApp: () -> Unit,
    openLogin: () -> Unit,
    openSignUp: () -> Unit,
    popUpScreen: () -> Unit
) {
    val viewModel = hiltViewModel<SettingsViewModel>()
    val uiState = viewModel.uiState.value

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicToolbar(AppText.settings) { popUpScreen() }

        Spacer(modifier = Modifier.fillMaxWidth().padding(12.dp))

        if (uiState.isAnonymousAccount) {
            CardEditor(AppText.sign_in, AppIcon.ic_sign_in, "") {
                openLogin()
            }

            CardEditor(AppText.create_account, AppIcon.ic_create_account, "") {
                openSignUp()
            }
        } else {
            //CREATE DANGER ZONE UI
            SignOutCard(restartApp, viewModel)
            DeleteMyAccountCard(restartApp, viewModel)
        }

        ClearAllTasksCard(viewModel)
    }
}

@Composable
private fun SignOutCard(restartApp: () -> Unit, viewModel: SettingsViewModel) {
    var showWarningDialog by remember { mutableStateOf(false) }

    CardEditor(AppText.sign_out, AppIcon.ic_exit, "") {
        showWarningDialog = true
    }

    if(showWarningDialog) { //EDIT DIALOG
        AlertDialog(
            title = { Text(stringResource(AppText.warning)) },
            text = { Text(stringResource(AppText.anonymous_warning)) },
            dismissButton = { DialogButton(AppText.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogButton(AppText.sign_out) {
                    viewModel.onSignOutClick(restartApp)
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}

@Composable
private fun DeleteMyAccountCard(restartApp: () -> Unit, viewModel: SettingsViewModel) {
    var showWarningDialog by remember { mutableStateOf(false) }

    CardEditor(AppText.delete_my_account, AppIcon.ic_delete_my_account, "") {
        showWarningDialog = true
    }

    if(showWarningDialog) { //EDIT DIALOG
        AlertDialog(
            title = { Text(stringResource(AppText.warning)) },
            text = { Text(stringResource(AppText.anonymous_warning)) },
            dismissButton = { DialogButton(AppText.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogButton(AppText.sign_out) {
                    viewModel.onDeleteMyAccountClick(restartApp)
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}

@Composable
private fun ClearAllTasksCard(viewModel: SettingsViewModel) {
    var showWarningDialog by remember { mutableStateOf(false) }

    CardEditor(AppText.clear_all_tasks, AppIcon.ic_clear_all_tasks, "") {
        showWarningDialog = true
    }

    if(showWarningDialog) { //EDIT DIALOG
        AlertDialog(
            title = { Text(stringResource(AppText.warning)) },
            text = { Text(stringResource(AppText.anonymous_warning)) },
            dismissButton = { DialogButton(AppText.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogButton(AppText.sign_out) {
                    viewModel.onClearAllTasksClick()
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}
