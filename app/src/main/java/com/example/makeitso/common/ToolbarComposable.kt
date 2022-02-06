package com.example.makeitso.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.makeitso.theme.MediumOrange

@Composable
fun ToolBar(
    @StringRes title: Int,
    hasBackAction: Boolean = false,
    @DrawableRes endActionIcon: Int? = null,
    endAction: () -> Unit = {}
) {
    if (endActionIcon == null) BasicToolbar(title, hasBackAction)
    else ActionToolbar(title, hasBackAction, endActionIcon, endAction)
}

@Composable
private fun BasicToolbar(@StringRes title: Int, hasBackAction: Boolean) {
    TopAppBar(
        title = { Text(stringResource(title)) },
        backgroundColor = MediumOrange,
        navigationIcon = if (hasBackAction) {
            {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        } else null
    )
}

@Composable
private fun ActionToolbar(
    @StringRes title: Int,
    hasBackAction: Boolean,
    @DrawableRes endActionIcon: Int,
    endAction: () -> Unit
) {
    TopAppBar(
        title = { Text(stringResource(title)) },
        backgroundColor = MediumOrange,
        navigationIcon = if (hasBackAction) {
            {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        } else null,
        actions = {
            Box(Modifier.wrapContentSize(Alignment.TopEnd)) {
                IconButton(onClick = endAction) {
                    Icon(painter = painterResource(endActionIcon), contentDescription = "Action")
                }
            }
        }
    )
}
