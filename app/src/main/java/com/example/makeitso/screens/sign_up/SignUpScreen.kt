package com.example.makeitso.screens.sign_up

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.makeitso.common.composable.*
import com.example.makeitso.common.ext.basicButton
import com.example.makeitso.common.ext.fieldModifier
import com.example.makeitso.R.string as AppText

@Composable
fun SignUpScreen(popUpScreen: () -> Unit) {
    val fieldModifier = Modifier.fieldModifier()
    val viewModel = hiltViewModel<SignUpViewModel>()
    val uiState = viewModel.uiState.value

    BasicToolbar(AppText.create_account) { popUpScreen() }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(uiState.email, fieldModifier, viewModel::onEmailChange)
        PasswordField(uiState.password, fieldModifier, viewModel::onPasswordChange)
        RepeatPasswordField(uiState.repeatPassword, fieldModifier, viewModel::onRepeatPasswordChange)

        BasicButton(AppText.create_account, Modifier.basicButton()) {
            viewModel.onSignUpClick(popUpScreen)
        }
    }
}
