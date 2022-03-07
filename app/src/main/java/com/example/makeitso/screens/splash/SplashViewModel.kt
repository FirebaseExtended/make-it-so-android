package com.example.makeitso.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.makeitso.common.navigation.SPLASH_SCREEN
import com.example.makeitso.common.navigation.TASKS_SCREEN
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

    fun onAppStart(navController: NavHostController) {
        if (accountService.hasUser()) navigateToTasks(navController)
        else createAnonymousAccount(navController)
    }

    private fun navigateToTasks(navController: NavHostController) {
        navController.navigate(TASKS_SCREEN) {
            popUpTo(SPLASH_SCREEN) { inclusive = true }
        }
    }

    private fun createAnonymousAccount(navController: NavHostController) {
        viewModelScope.launch(exceptionHandler) {
            accountService.createAnonymousAccount { task ->
                if (task.isSuccessful) navigateToTasks(navController)
                else crashlyticsService.logNonFatalCrash(task.exception)
            }
        }
    }
}