package com.example.makeitso.model

import androidx.annotation.ColorRes
import com.example.makeitso.R.color as AppColor

enum class Priority(@ColorRes val color: Int) {
    None(AppColor.black),
    Low(AppColor.green),
    Medium(AppColor.yellow),
    High(AppColor.red);

    companion object {
        fun getByName(name: String?): Priority {
            values().forEach { priority ->
                if (name == priority.name) return priority
            }

            return None
        }

        fun getAllNames(): List<String> {
            val names = mutableListOf<String>()
            values().forEach { priority -> names.add(priority.name) }
            return names
        }
    }
}
