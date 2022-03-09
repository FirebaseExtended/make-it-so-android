package com.example.makeitso.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.service.CrashlyticsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService,
    private val crashlyticsService: CrashlyticsService
) : ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(throwable) }
    }

    fun onAppStart(openTasks: () -> Unit) {
        if (accountService.hasUser()) openTasks()
        else createAnonymousAccount(openTasks)
    }

    private fun createAnonymousAccount(openTasks: () -> Unit) {
        viewModelScope.launch(exceptionHandler) {
            accountService.createAnonymousAccount { task ->
                if (task.isSuccessful) openTasks()
                else crashlyticsService.logNonFatalCrash(task.exception)
            }
        }
    }
}