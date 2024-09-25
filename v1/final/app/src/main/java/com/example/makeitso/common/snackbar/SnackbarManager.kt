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

import androidx.annotation.StringRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object SnackbarManager {
  private val messages: MutableStateFlow<SnackbarMessage?> = MutableStateFlow(null)
  val snackbarMessages: StateFlow<SnackbarMessage?>
    get() = messages.asStateFlow()

  fun showMessage(@StringRes message: Int) {
    messages.value = SnackbarMessage.ResourceSnackbar(message)
  }

  fun showMessage(message: SnackbarMessage) {
    messages.value = message
  }

  fun clearSnackbarState() {
    messages.value = null
  }
}
