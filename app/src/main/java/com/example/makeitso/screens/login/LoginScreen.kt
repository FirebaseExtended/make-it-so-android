package com.example.makeitso.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.makeitso.common.composable.*
import com.example.makeitso.R.string as AppText

@Composable
fun LoginScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<LoginViewModel>()
    val uiState = viewModel.uiState.value

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    BasicToolbar(AppText.login_details)

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(email)
        PasswordField(password)

        BasicButton(AppText.sign_in) {
            viewModel.onSignInClick(navController, email.value, password.value)
        }

        BasicTextButton(AppText.do_not_have_account) {
            viewModel.onSignUpClick(navController)
        }

        if (uiState === LoginUiState.ErrorState) {
            BasicText(text = AppText.login_error, color = Color.Red)
        }
    }
}