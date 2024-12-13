package com.google.firebase.example.makeitso.data.model

import androidx.compose.ui.graphics.Color
import com.google.firebase.example.makeitso.ui.theme.LightGreen
import com.google.firebase.example.makeitso.ui.theme.LightOrange
import com.google.firebase.example.makeitso.ui.theme.LightRed

enum class Priority(val title: String, val selectedColor: Color, val value: Int) {
    LOW("Low", LightGreen, 2),
    MEDIUM("Medium", LightOrange, 1),
    HIGH("High", LightRed, 0)
}