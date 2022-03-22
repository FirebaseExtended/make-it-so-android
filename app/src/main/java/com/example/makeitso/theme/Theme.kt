package com.example.makeitso.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = BrightOrange,
    primaryVariant = MediumOrange,
    secondary = DarkOrange,
    onSecondary = Color.LightGray,
    surface = Color.Gray,
    onSurface = Color.DarkGray,
    onPrimary = Color.White,
    background = Color.Black,
    onBackground = Color.White
)

private val LightColorPalette = lightColors(
    primary = BrightOrange,
    primaryVariant = MediumOrange,
    secondary = DarkOrange,
    onSecondary = Color.LightGray,
    surface = Color.Gray,
    onSurface = Color.DarkGray,
    onPrimary = Color.White,
    background = Color.White,
    onBackground = Color.Black
)

@Composable
fun MakeItSoTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}