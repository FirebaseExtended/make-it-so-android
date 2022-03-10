package com.example.makeitso.screens.sign_up

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.makeitso.common.composable.*
import com.example.makeitso.R.string as AppText

@Composable
fun SignUpScreen(popUpScreen: () -> Unit) {
    val viewModel = hiltViewModel<SignUpViewModel>()
    val uiState = viewModel.uiState.value

    BasicToolbar(AppText.create_an_account) { popUpScreen() }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(uiState.email, viewModel::onEmailChange)
        PasswordField(uiState.password, viewModel::onPasswordChange)
        RepeatPasswordField(uiState.repeatPassword, viewModel::onRepeatPasswordChange)

        BasicButton(AppText.create_account) { viewModel.onSignUpClick(popUpScreen) }
    }
}
