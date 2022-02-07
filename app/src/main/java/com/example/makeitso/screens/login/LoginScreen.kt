package com.example.makeitso.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.makeitso.common.composable.*
import com.example.makeitso.common.navigation.SIGN_UP_SCREEN
import com.example.makeitso.common.navigation.TASKS_SCREEN
import com.example.makeitso.R.string as AppText

@Composable
fun LoginScreen(navController: NavHostController) {
    BasicToolbar(AppText.login_details)

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        EmailField()
        PasswordField()

        BasicButton(AppText.sign_in) {
            //Call ViewModel for login logic
            navController.navigate(TASKS_SCREEN)
        }

        BasicTextButton(AppText.do_not_have_account) {
            navController.navigate(SIGN_UP_SCREEN)
        }
    }
}