package com.google.firebase.example.makeitso

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class MainViewModel() : ViewModel() {
    fun launchCatching(
        showErrorSnackbar: (String) -> Unit = {},
        block: suspend CoroutineScope.() -> Unit
    ) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                Firebase.crashlytics.recordException(throwable)
                showErrorSnackbar(throwable.message ?: GENERIC_ERROR)
            },
            block = block
        )

    companion object {
        private const val GENERIC_ERROR = "Something went wrong"
    }
}