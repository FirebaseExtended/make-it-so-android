package com.example.makeitso.screens.sign_up

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.makeitso.common.composable.*
import com.example.makeitso.common.navigation.LOGIN_SCREEN
import com.example.makeitso.common.navigation.TASKS_SCREEN
import com.example.makeitso.R.string as AppText

@Composable
fun SignUpScreen(navController: NavHostController) {
    BasicToolbar(AppText.create_an_account) {
        navController.popBackStack()
    }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        BasicField(AppText.name, Icons.Filled.Person)
        EmailField()
        PasswordField()

        BasicButton(AppText.create_account) {
            //Call ViewModel for sign up logic
            navController.navigate(TASKS_SCREEN) {
                popUpTo(LOGIN_SCREEN) { inclusive = true }
            }
        }

        BasicTextButton(AppText.sign_up_anonymously) {
            //Call ViewModel for anonymous sign up logic
            navController.navigate(TASKS_SCREEN) {
                popUpTo(LOGIN_SCREEN) { inclusive = true }
            }
        }
    }
}