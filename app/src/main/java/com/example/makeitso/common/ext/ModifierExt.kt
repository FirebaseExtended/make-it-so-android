package com.example.makeitso.common.ext

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/*
 * Only Extensions used by more than one screen
 */

fun Modifier.paddingStart(dimension: Dp): Modifier {
    return this.padding(dimension, 0.dp, 0.dp, 0.dp)
}

fun Modifier.paddingEnd(dimension: Dp): Modifier {
    return this.padding(0.dp, 0.dp, dimension, 0.dp)
}