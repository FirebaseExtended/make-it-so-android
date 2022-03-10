package com.example.makeitso.common.snackbar

import androidx.annotation.StringRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object SnackbarManager {
    private val messages: MutableStateFlow<List<SnackbarMessage>> = MutableStateFlow(emptyList())
    val snackbarMessages: StateFlow<List<SnackbarMessage>> get() = messages.asStateFlow()

    fun showMessage(@StringRes message: Int) {
        messages.update { currentMessages ->
            currentMessages + SnackbarMessage.ResourceSnackbar(message)
        }
    }

    fun showMessage(message: SnackbarMessage) {
        messages.update { currentMessages ->
            currentMessages + message
        }
    }
}