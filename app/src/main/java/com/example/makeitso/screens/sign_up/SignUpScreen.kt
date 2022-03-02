package com.example.makeitso.screens.sign_up

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.makeitso.common.composable.*
import com.example.makeitso.common.error.ErrorMessage.Companion.toMessage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import com.example.makeitso.R.string as AppText

@Composable
fun SignUpScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<SignUpViewModel>()

    val context = LocalContext.current
    val snackbarChannel = remember { viewModel.snackbarChannel }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        snackbarChannel.receiveAsFlow().collect { errorMessage ->
            snackbarHostState.showSnackbar(errorMessage.toMessage(context))
        }
    }

    Scaffold(scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)) {
        ScreenContent(navController, viewModel)
    }

    BackHandler { viewModel.onBackClick(navController) }
}

@Composable
private fun ScreenContent(navController: NavHostController, viewModel: SignUpViewModel) {
    val uiState = viewModel.uiState.value
    var showWarningDialog by remember { mutableStateOf(false) }

    BasicToolbar(AppText.create_an_account) {
        viewModel.onBackClick(navController)
    }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(uiState.email, viewModel::onEmailChange)
        PasswordField(uiState.password, viewModel::onPasswordChange)
        RepeatPasswordField(uiState.repeatPassword, viewModel::onRepeatPasswordChange)

        BasicButton(AppText.create_account) {
            viewModel.onSignUpClick(navController)
        }

        BasicTextButton(AppText.sign_up_anonymously) {
            showWarningDialog = true
        }

        if(showWarningDialog) {
            AlertDialog(
                title = { Text(stringResource(AppText.anonymous_warning_title)) },
                text = { Text(stringResource(AppText.anonymous_warning_description)) },
                dismissButton = { DialogButton(AppText.cancel) { showWarningDialog = false } },
                confirmButton = {
                    DialogButton(AppText.proceed) {
                        viewModel.onAnonymousSignUpClick(navController)
                        showWarningDialog = false
                    }
                },
                onDismissRequest = { showWarningDialog = false }
            )
        }
    }
}