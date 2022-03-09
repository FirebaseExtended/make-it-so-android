package com.example.makeitso.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.makeitso.common.composable.*
import com.example.makeitso.common.error.ErrorMessage.Companion.toMessage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import com.example.makeitso.R.string as AppText

@Composable
fun LoginScreen(openSignUp: () -> Unit, popUpScreen: () -> Unit) {
    val viewModel = hiltViewModel<LoginViewModel>()

    val context = LocalContext.current
    val snackbarChannel = remember { viewModel.snackbarChannel }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        snackbarChannel.receiveAsFlow().collect { errorMessage ->
            snackbarHostState.showSnackbar(errorMessage.toMessage(context))
        }
    }

    Scaffold(scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)) {
        ScreenContent(openSignUp, popUpScreen, viewModel)
    }
}

@Composable
private fun ScreenContent(
    openSignUp: () -> Unit,
    popUpScreen: () -> Unit,
    viewModel: LoginViewModel
) {
    val uiState = viewModel.uiState.value

    BasicToolbar(AppText.login_details) { popUpScreen() }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(uiState.email, viewModel::onEmailChange)
        PasswordField(uiState.password, viewModel::onPasswordChange)

        BasicButton(AppText.sign_in) { viewModel.onSignInClick(popUpScreen) }

        BasicTextButton(AppText.do_not_have_account) { openSignUp() }

        BasicTextButton(AppText.forgot_password) { viewModel.onForgotPasswordClick() }
    }
}