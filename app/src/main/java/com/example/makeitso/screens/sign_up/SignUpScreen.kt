package com.example.makeitso.screens.sign_up

import androidx.activity.compose.BackHandler
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
fun SignUpScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<SignUpViewModel>()
    val uiState = viewModel.uiState.value

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    BasicToolbar(AppText.create_an_account) {
        viewModel.onBackClick(navController)
    }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(email)
        PasswordField(password)

        BasicButton(AppText.create_account) {
            viewModel.onSignUpClick(navController, email.value, password.value)
        }

        BasicTextButton(AppText.sign_up_anonymously) {
            viewModel.onAnonymousSignUpClick(navController)
        }

        if (uiState === SignUpUiState.ErrorState) {
            BasicText(text = AppText.sign_up_error, color = Color.Red)
        }
    }

    BackHandler { viewModel.onBackClick(navController) }
}