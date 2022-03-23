package com.example.makeitso.common.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun BasicToolbar(@StringRes title: Int, backAction: (() -> Unit)? = null) {
    TopAppBar(
        title = { Text(stringResource(title)) },
        backgroundColor = ToolbarColor(),
        navigationIcon = if (backAction != null) {
            {
                IconButton(onClick = backAction) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        } else null
    )
}

@Composable
fun ActionToolbar(
    @StringRes title: Int,
    @DrawableRes endActionIcon: Int,
    modifier: Modifier,
    endAction: () -> Unit
) {
    TopAppBar(
        title = { Text(stringResource(title)) },
        backgroundColor = ToolbarColor(),
        actions = {
            Box(modifier) {
                IconButton(onClick = endAction) {
                    Icon(painter = painterResource(endActionIcon), contentDescription = "Action")
                }
            }
        }
    )
}

@Composable
private fun ToolbarColor(darkTheme: Boolean = isSystemInDarkTheme()): Color {
    return if (darkTheme) MaterialTheme.colors.secondary else MaterialTheme.colors.primaryVariant
}
