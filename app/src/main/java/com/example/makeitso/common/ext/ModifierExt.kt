package com.example.makeitso.common.ext

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.textButton(): Modifier {
    return this.fillMaxWidth().padding(16.dp, 8.dp, 16.dp, 0.dp)
}

fun Modifier.basicButton(): Modifier {
    return this.fillMaxWidth().padding(16.dp, 8.dp)
}

fun Modifier.card(): Modifier {
    return this.padding(16.dp, 0.dp, 16.dp, 8.dp)
}

fun Modifier.contextMenu(): Modifier {
    return this.wrapContentWidth()
}

fun Modifier.dropdownSelector(): Modifier {
    return this.fillMaxWidth()
}

fun Modifier.fieldModifier(): Modifier {
    return this.fillMaxWidth().padding(16.dp, 4.dp)
}

fun Modifier.toolbarActions(): Modifier {
    return this.wrapContentSize(Alignment.TopEnd)
}
