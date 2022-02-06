package com.example.makeitso.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.makeitso.common.*
import com.example.makeitso.R.string as AppText

@Composable
fun LoginScreen() {
    ToolBar(AppText.login_details)

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        EmailField()
        PasswordField()
        BasicButton(AppText.sign_in) { /* Call ViewModel */ }
        BasicTextButton(AppText.do_not_have_account) { /* Call ViewModel */ }
    }
}