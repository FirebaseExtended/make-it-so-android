package com.example.makeitso.screens.sign_up

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
            viewModel.onAnonymousSignUpClick(navController)
        }
    }
}