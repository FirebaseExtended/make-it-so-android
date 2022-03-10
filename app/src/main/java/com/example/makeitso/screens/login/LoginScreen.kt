package com.example.makeitso.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.makeitso.common.composable.*
import com.example.makeitso.R.string as AppText

@Composable
fun LoginScreen(openSignUp: () -> Unit, popUpScreen: () -> Unit) {
    val viewModel = hiltViewModel<LoginViewModel>()
    val uiState = viewModel.uiState.value

    BasicToolbar(AppText.login_details) { popUpScreen() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
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
