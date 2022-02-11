package com.example.makeitso.screens.edit_task

enum class EditFlagOptions {
    On,
    Off;

    companion object {
        fun getByCheckedState(checkedState: Boolean?): EditFlagOptions {
            val hasFlag = checkedState ?: false
            return if (hasFlag) On else Off
        }

        fun getOptions(): List<String> {
            val options = mutableListOf<String>()
            values().forEach { flagOption -> options.add(flagOption.name) }
            return options
        }
    }
}
