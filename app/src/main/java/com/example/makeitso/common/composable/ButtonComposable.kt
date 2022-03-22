package com.example.makeitso.common.composable

import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.makeitso.theme.BrightOrange

@Composable
fun BasicTextButton(@StringRes text: Int, modifier: Modifier, action: () -> Unit) {
    TextButton(onClick = action, modifier = modifier) {
        Text(text = stringResource(text), color = Color.Gray)
    }
}

@Composable
fun BasicButton(@StringRes text: Int, modifier: Modifier, action: () -> Unit) {
    Button(
        onClick = action,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = BrightOrange,
            contentColor = Color.White
        )
    ) {
        Text(text = stringResource(text), fontSize = 16.sp)
    }
}

@Composable
fun DialogConfirmButton(@StringRes text: Int, action: () -> Unit) {
    Button(
        onClick = action,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = BrightOrange,
            contentColor = Color.White
        )
    ) {
        Text(text = stringResource(text))
    }
}

@Composable
fun DialogCancelButton(@StringRes text: Int, action: () -> Unit) {
    Button(
        onClick = action,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = BrightOrange
        )
    ) {
        Text(text = stringResource(text))
    }
}
