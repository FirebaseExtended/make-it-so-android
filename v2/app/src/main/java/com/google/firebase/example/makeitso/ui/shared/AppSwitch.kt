package com.google.firebase.example.makeitso.ui.shared

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.google.firebase.example.makeitso.ui.theme.DarkBlue
import com.google.firebase.example.makeitso.ui.theme.LightBlue
import com.google.firebase.example.makeitso.ui.theme.LightYellow
import com.google.firebase.example.makeitso.ui.theme.MediumGrey

@Composable
fun AppSwitch(
    initialValue: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Switch(
        modifier = Modifier.scale(0.8f).padding(end = 8.dp),
        checked = initialValue,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = DarkBlue,
            checkedTrackColor = LightBlue,
            uncheckedThumbColor = MediumGrey,
            uncheckedTrackColor = LightYellow,
        )
    )
}