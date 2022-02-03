package com.example.makeitso.model

import androidx.annotation.ColorRes
import com.example.makeitso.R.color as Color

enum class Priority(@ColorRes val color: Int) {
    NONE(Color.black),
    LOW(Color.green),
    MEDIUM(Color.yellow),
    HIGH(Color.red)
}

