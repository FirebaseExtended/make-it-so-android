package com.example.makeitso.common

import androidx.annotation.StringRes
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.makeitso.theme.MediumOrange

//Need to add left and top actions when needed
@Composable
fun ToolBar(@StringRes title: Int) {
    TopAppBar(
        title = { Text(stringResource(title)) },
        backgroundColor = MediumOrange
    )
}