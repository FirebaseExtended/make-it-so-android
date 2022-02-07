package com.example.makeitso.common.composable

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.makeitso.theme.BrightOrange

@Composable
fun ThemedSwitch(checked: Boolean) {
    Switch(
        checked = checked,
        onCheckedChange = {},
        colors = SwitchDefaults.colors(
            checkedThumbColor = BrightOrange,
            uncheckedThumbColor = Color.DarkGray,
            checkedTrackColor = BrightOrange,
            uncheckedTrackColor = Color.DarkGray
        )
    )
}
