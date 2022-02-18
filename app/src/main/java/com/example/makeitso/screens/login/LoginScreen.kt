package com.example.makeitso.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.makeitso.common.composable.*
import com.example.makeitso.R.string as AppText

@Composable
fun LoginScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<LoginViewModel>()
    val uiState = viewModel.uiState.value

    BasicToolbar(AppText.login_details)

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(uiState.email, viewModel::onEmailChange)
        PasswordField(uiState.password, viewModel::onPasswordChange)

        BasicButton(AppText.sign_in) {
            viewModel.onSignInClick(navController)
        }

        BasicTextButton(AppText.do_not_have_account) {
            viewModel.onSignUpClick(navController)
        }
    }
}