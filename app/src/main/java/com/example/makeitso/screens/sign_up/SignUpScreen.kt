package com.example.makeitso.screens.sign_up

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.makeitso.common.*
import com.example.makeitso.theme.BrightOrange
import com.example.makeitso.R.string as AppText

//This method should be private and part of a fragment in the future

@Composable
fun SignUpScreen() {
    Column( modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
        ToolBar(AppText.sign_up, hasBackAction = true)

        Spacer(modifier = Modifier.fillMaxWidth().height(32.dp))

        Text(
            modifier = Modifier.wrapContentSize().align(Alignment.CenterHorizontally),
            text = stringResource(AppText.create_account),
            color = BrightOrange,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        BasicField()
        EmailField()
        PasswordField()
    }
}