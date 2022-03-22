package com.example.makeitso.common.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.makeitso.common.ext.dropdownSelector
import com.example.makeitso.theme.BrightOrange

@Composable
fun DangerousCardEditor(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    content: String,
    modifier: Modifier,
    onEditClick: () -> Unit
) {
    CardEditor(title, icon, content, onEditClick, BrightOrange, modifier)
}

@Composable
fun RegularCardEditor(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    content: String,
    modifier: Modifier,
    onEditClick: () -> Unit
) {
    CardEditor(title, icon, content, onEditClick, Color.DarkGray, modifier)
}

@Composable
private fun CardEditor(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    content: String,
    onEditClick: () -> Unit,
    highlightColor: Color,
    modifier: Modifier
) {
    Card(backgroundColor = Color.White, modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(16.dp, 4.dp, 0.dp, 4.dp),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(stringResource(title), color = highlightColor)
            }

            if (content.isNotBlank()) {
                Text(
                    text = content,
                    color = Color.LightGray,
                    modifier = Modifier.padding(16.dp, 0.dp)
                )
            }

            IconButton(onClick = onEditClick) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = "Icon",
                    tint = highlightColor
                )
            }
        }
    }
}

@Composable
@ExperimentalMaterialApi
fun CardSelector(
    @StringRes label: Int,
    options: List<String>,
    selection: String,
    modifier: Modifier,
    onNewValue: (String) -> Unit
) {
    Card(backgroundColor = Color.White, modifier = modifier) {
        DropdownSelector(label, options, selection, Modifier.dropdownSelector(), onNewValue)
    }
}
