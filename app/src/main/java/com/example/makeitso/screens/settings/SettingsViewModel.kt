package com.example.makeitso.screens.settings

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
class SettingsViewModel @Inject constructor(
    private val accountService: AccountService,
    private val crashlyticsService: CrashlyticsService
) : ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        //add snack bar error message
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(throwable) }
    }

    fun onBackClick(navController: NavHostController) {
        navController.popBackStack()
    }

    fun onLinkAccountClick(navController: NavHostController) {
        //call login screen
        //modify login and sign up screen results
        //verify how backstack will behave
    }

    fun onSignOutClick(navController: NavHostController) {
        //first verify if it's really logged anonymously
        viewModelScope.launch(exceptionHandler) {
            accountService.signOut()

            navController.navigate(SPLASH_SCREEN) {
                popUpTo(TASKS_SCREEN) { inclusive = true }
            }
        }
    }
}