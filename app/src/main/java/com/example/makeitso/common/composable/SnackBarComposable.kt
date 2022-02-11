package com.example.makeitso.common.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun BottomSnackBar(@StringRes text: Int) {
    Snackbar(modifier = Modifier.padding(8.dp)) {
        Text(stringResource(text))
    }
}
