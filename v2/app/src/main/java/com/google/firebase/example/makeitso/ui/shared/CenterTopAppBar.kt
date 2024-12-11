package com.google.firebase.example.makeitso.ui.shared

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import com.google.firebase.example.makeitso.ui.theme.DarkGrey
import com.google.firebase.example.makeitso.ui.theme.LightYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterTopAppBar(
    title: String,
    icon: ImageVector,
    iconDescription: String,
    action: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = if (isSystemInDarkTheme()) DarkGrey else LightYellow
        ),
        title = {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(onClick = { action() }) {
                Icon(
                    imageVector = icon,
                    contentDescription = iconDescription
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}
