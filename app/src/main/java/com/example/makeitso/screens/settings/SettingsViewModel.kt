package com.example.makeitso.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makeitso.common.snackbar.SnackbarManager
import com.example.makeitso.R.string as AppText
import com.example.makeitso.model.database.repository.TaskRepository
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.service.CrashlyticsService
import com.example.makeitso.model.service.FirestoreService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
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

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        SnackbarManager.showMessage(AppText.generic_error)
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(throwable) }
    }

    fun initialize() {
        uiState.value = SettingsUiState(accountService.isAnonymousUser())
    }

    fun onSignOutClick(signOut: () -> Unit) {
        viewModelScope.launch(exceptionHandler) {
            if (uiState.value.isAnonymousAccount) clearAnonymousAccount()

            accountService.signOut()
            signOut()
        }
    }

    private fun clearAnonymousAccount() {
        viewModelScope.launch(exceptionHandler) {
            val userId = accountService.getAnonymousUserId()

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