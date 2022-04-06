package com.example.makeitso.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makeitso.common.snackbar.SnackbarManager
import com.example.makeitso.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.makeitso.model.service.CrashlyticsService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

open class MakeItSoViewModel(private val crashlyticsService: CrashlyticsService) : ViewModel() {
    open val showErrorExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    }

    open val logErrorExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(throwable) }
    }

    open fun onError(error: Throwable?) {
        SnackbarManager.showMessage(error.toSnackbarMessage())
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(error) }
    }
}