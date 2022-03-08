package com.example.makeitso.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.error.ErrorMessage
import com.example.makeitso.common.navigation.LOGIN_SCREEN
import com.example.makeitso.common.navigation.SETTINGS_SCREEN
import com.example.makeitso.common.navigation.SPLASH_SCREEN
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.service.CrashlyticsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val accountService: AccountService,
    private val crashlyticsService: CrashlyticsService
) : ViewModel() {
    val snackbarChannel = Channel<ErrorMessage>(Channel.CONFLATED)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        snackbarChannel.trySend(ErrorMessage.ResourceError(AppText.generic_error))
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(throwable) }
    }

    fun onBackClick(navController: NavHostController) {
        navController.popBackStack()
    }

    fun onLinkAccountClick(navController: NavHostController) {
        navController.navigate(LOGIN_SCREEN)
    }

    fun onSignOutClick(navController: NavHostController) {
        //first verify if it's really logged anonymously
        //if confirms to sign out anon, clear firestore + db
        viewModelScope.launch(exceptionHandler) {
            accountService.signOut()

            navController.navigate(SPLASH_SCREEN) {
                popUpTo(SETTINGS_SCREEN) { inclusive = true } //how to clear up all history?
            }
        }
    }
}