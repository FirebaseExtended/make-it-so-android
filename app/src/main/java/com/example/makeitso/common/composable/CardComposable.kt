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
import com.example.makeitso.common.ext.paddingEnd
import com.example.makeitso.theme.BrightOrange

@Composable
fun CardSwitch(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    checked: Boolean,
    content: String = ""
) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 4.dp),
        ) {
            Icon(
                modifier = Modifier.paddingEnd(8.dp),
                painter = painterResource(icon),
                contentDescription = "Icon",
                tint = BrightOrange
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(stringResource(title), color = Color.DarkGray)
            }

            if (content.isNotBlank()) {
                Text(
                    text = content,
                    color = Color.LightGray,
                    modifier = Modifier.padding(16.dp, 0.dp)
                )
            }

            ThemedSwitch(checked)
        }
    }
}

@Composable
@ExperimentalMaterialApi
fun CardSelector(@StringRes label: Int, options: List<String>, selection: String) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 4.dp)
    ) {
        DropdownMenu(label, options, selection)
    }
}
