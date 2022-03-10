package com.example.makeitso.common.snackbar

import android.content.res.Resources
import androidx.annotation.StringRes
import com.example.makeitso.R.string as AppText

sealed class SnackbarMessage {
    class StringSnackbar(val message: String): SnackbarMessage()
    class ResourceSnackbar(@StringRes val message: Int): SnackbarMessage()

    companion object {
        fun SnackbarMessage.toMessage(resources: Resources): String {
            return when (this) {
                is StringSnackbar -> this.message
                is ResourceSnackbar -> resources.getString(this.message)
            }
        }

        fun Throwable?.toSnackbarMessage(): SnackbarMessage {
            if (this == null) return ResourceSnackbar(AppText.generic_error)

            val message = this.message.orEmpty()

            return if (message.isNotBlank()) StringSnackbar(message)
            else ResourceSnackbar(AppText.generic_error)
        }
    }
}
