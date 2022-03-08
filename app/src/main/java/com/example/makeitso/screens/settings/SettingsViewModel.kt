package com.example.makeitso.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.error.ErrorMessage
import com.example.makeitso.common.navigation.LOGIN_SCREEN
import com.example.makeitso.common.navigation.SETTINGS_SCREEN
import com.example.makeitso.common.navigation.SPLASH_SCREEN
import com.example.makeitso.model.database.repository.TaskRepository
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.service.CrashlyticsService
import com.example.makeitso.model.service.FirestoreService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val crashlyticsService: CrashlyticsService,
    private val taskRepository: TaskRepository
) : ViewModel() {
    var uiState = mutableStateOf(SettingsUiState())
        private set

    val snackbarChannel = Channel<ErrorMessage>(Channel.CONFLATED)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        snackbarChannel.trySend(ErrorMessage.ResourceError(AppText.generic_error))
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(throwable) }
    }

    fun initialize() {
        uiState.value = SettingsUiState(accountService.isAnonymousUser())
    }

    fun onBackClick(navController: NavHostController) {
        navController.popBackStack()
    }

    fun onLinkAccountClick(navController: NavHostController) {
        navController.navigate(LOGIN_SCREEN)
    }

    fun onSignOutClick(navController: NavHostController) {
        viewModelScope.launch(exceptionHandler) {
            if (uiState.value.isAnonymousAccount) clearAnonymousAccount()

            accountService.signOut()

            navController.navigate(SPLASH_SCREEN) {
                popUpTo(SETTINGS_SCREEN) { inclusive = true } //how to clear up all history?
            }
        }
    }

    private fun clearAnonymousAccount() {
        viewModelScope.launch(exceptionHandler) {
            val userId = accountService.getUserId()

            firestoreService.deleteAllForUser(userId) { error ->
                if (error == null) {
                    viewModelScope.launch { taskRepository.deleteAllForUser(userId) }
                } else {
                    viewModelScope.launch { crashlyticsService.logNonFatalCrash(error) }
                }
            }
        }
    }
}