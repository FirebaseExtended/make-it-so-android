package com.example.makeitso.common.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.makeitso.common.ext.paddingEnd
import com.example.makeitso.common.ext.paddingStart
import com.example.makeitso.theme.BrightOrange

@Composable
fun CardSwitch(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    checked: Boolean,
    content: String = ""
) {
    Card(backgroundColor = Color.White, modifier = Modifier.cardModifier()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.rowModifier(),
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
fun CardSelector(@StringRes title: Int, @StringRes selection: Int) {
    Card(backgroundColor = Color.White, modifier = Modifier.cardModifier(),) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.rowModifier(),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(stringResource(title), color = Color.DarkGray)
            }

            Text(stringResource(selection), color = Color.LightGray)

            Icon(
                modifier = Modifier.paddingStart(8.dp),
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Arrow",
                tint = BrightOrange
            )
        }
    }
}

private fun Modifier.cardModifier(): Modifier {
    return this.padding(16.dp, 0.dp, 16.dp, 8.dp)
}

private fun Modifier.rowModifier(): Modifier {
    return this.fillMaxWidth().padding(8.dp, 16.dp)
}
