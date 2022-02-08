package com.example.makeitso.model

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.example.makeitso.R.color as AppColor
import com.example.makeitso.R.string as AppText

enum class Priority(@ColorRes val color: Int, @StringRes val title: Int) {
    NONE(AppColor.black, AppText.none),
    LOW(AppColor.green, AppText.low),
    MEDIUM(AppColor.yellow, AppText.medium),
    HIGH(AppColor.red, AppText.high);

    companion object {
        fun getByName(name: String?): Priority {
            values().forEach { priority ->
                if (name == priority.name) return priority
            }

            return NONE
        }
    }
}
