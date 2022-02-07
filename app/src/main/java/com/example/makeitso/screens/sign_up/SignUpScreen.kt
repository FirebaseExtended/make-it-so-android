package com.example.makeitso.screens.sign_up

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.makeitso.common.*
import com.example.makeitso.R.string as AppText

@Composable
fun SignUpScreen() {
    ToolBar(AppText.create_an_account, hasBackAction = true)

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        BasicField(AppText.name, Icons.Filled.Person)
        EmailField()
        PasswordField()
        BasicButton(AppText.create_account) { /* Call ViewModel */ }
        BasicTextButton(AppText.sign_up_anonymously) { /* Call ViewModel */ }
    }
}