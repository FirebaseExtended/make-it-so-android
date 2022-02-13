package com.example.makeitso.common.composable

import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@Composable
fun BasicText(@StringRes text: Int, color: Color) {
    Text(text = stringResource(text), color = color)
}
