package com.example.makeitso.screens.sign_up

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.makeitso.common.composable.*
import com.example.makeitso.R.string as AppText

@Composable
fun SignUpScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<SignUpViewModel>()

    BasicToolbar(AppText.create_an_account) {
        viewModel.onBackClick(navController)
    }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        BasicField(AppText.name, Icons.Filled.Person)
      //  EmailField()
      //  PasswordField()

        BasicButton(AppText.create_account) {
            viewModel.onSignUpClick(navController)
        }

        BasicTextButton(AppText.sign_up_anonymously) {
            viewModel.onAnonymousSignUpClick(navController)
        }
    }

    BackHandler { viewModel.onBackClick(navController) }
}