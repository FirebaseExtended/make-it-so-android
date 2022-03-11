package com.example.makeitso.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makeitso.common.snackbar.SnackbarManager
import com.example.makeitso.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
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

    fun onSignOutClick(restartApp: () -> Unit) {
        viewModelScope.launch(exceptionHandler) {
            accountService.signOut()
            restartApp()
        }
    }

    fun onDeleteMyAccountClick(restartApp: () -> Unit) {
        onClearAllTasksClick(shouldDisplayMessage = false)

        viewModelScope.launch(exceptionHandler) {
            accountService.deleteAccount { error ->
                if (error == null) {
                    restartApp()
                } else {
                    SnackbarManager.showMessage(error.toSnackbarMessage())
                    crashlyticsService.logNonFatalCrash(error)
                }
            }
        }
    }

    fun onClearAllTasksClick(shouldDisplayMessage: Boolean = true) {
        viewModelScope.launch(exceptionHandler) {
            val userId = accountService.getUserId()

            firestoreService.deleteAllForUser(userId) { error ->
                if (error == null) {
                    if (shouldDisplayMessage) SnackbarManager.showMessage(AppText.tasks_cleared)
                    viewModelScope.launch { taskRepository.deleteAllForUser(userId) }
                } else {
                    viewModelScope.launch { crashlyticsService.logNonFatalCrash(error) }
                }
            }
        }
    }
}