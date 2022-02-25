package com.example.makeitso.common.error

import android.content.Context
import androidx.annotation.StringRes
import com.example.makeitso.R.string as AppText
import java.lang.Exception

sealed class ErrorMessage {
    class StringError(val message: String): ErrorMessage()
    class ResourceError(@StringRes val message: Int): ErrorMessage()

    companion object {
        fun ErrorMessage.toMessage(context: Context): String {
            return when (this) {
                is StringError -> this.message
                is ResourceError -> context.getString(this.message)
            }
        }

        fun Exception?.toErrorMessage(): ErrorMessage {
            if (this == null) return ResourceError(AppText.generic_error)

            val message = this.message.orEmpty()

            return if (message.isNotBlank()) StringError(message)
            else ResourceError(AppText.generic_error)
        }
    }
}
