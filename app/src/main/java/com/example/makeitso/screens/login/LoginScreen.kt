package com.example.makeitso.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.makeitso.common.composable.*
import com.example.makeitso.common.ext.basicButton
import com.example.makeitso.common.ext.fieldModifier
import com.example.makeitso.common.ext.textButton
import com.example.makeitso.R.string as AppText

@Composable
fun LoginScreen(popUpScreen: () -> Unit) {
    val viewModel = hiltViewModel<LoginViewModel>()
    val uiState = viewModel.uiState.value

    BasicToolbar(AppText.login_details) { popUpScreen() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(uiState.email, Modifier.fieldModifier(), viewModel::onEmailChange)
        PasswordField(uiState.password, Modifier.fieldModifier(), viewModel::onPasswordChange)

        BasicButton(AppText.sign_in, Modifier.basicButton()) {
            viewModel.onSignInClick(popUpScreen)
        }

        BasicTextButton(AppText.forgot_password, Modifier.textButton()) {
            viewModel.onForgotPasswordClick()
        }
    }
}
