/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.example.makeitso.common.snackbar

import android.content.res.Resources
import androidx.annotation.StringRes
import com.example.makeitso.R.string as AppText

sealed class SnackbarMessage {
  class StringSnackbar(val message: String) : SnackbarMessage()
  class ResourceSnackbar(@StringRes val message: Int) : SnackbarMessage()

  companion object {
    fun SnackbarMessage.toMessage(resources: Resources): String {
      return when (this) {
        is StringSnackbar -> this.message
        is ResourceSnackbar -> resources.getString(this.message)
      }
    }

    fun Throwable.toSnackbarMessage(): SnackbarMessage {
      val message = this.message.orEmpty()
      return if (message.isNotBlank()) StringSnackbar(message)
      else ResourceSnackbar(AppText.generic_error)
    }
  }
}
