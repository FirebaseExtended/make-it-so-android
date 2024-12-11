package com.google.firebase.example.makeitso

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class MainViewModel : ViewModel() {
    fun launchCatching(
        showErrorSnackbar: (String) -> Unit = {},
        block: suspend CoroutineScope.() -> Unit
    ) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                val message = throwable.message ?: GENERIC_ERROR
                Log.e(this.javaClass.name, message)
                showErrorSnackbar(message)
            },
            block = block
        )

    companion object {
        private const val GENERIC_ERROR = "Something went wrong"
    }
}