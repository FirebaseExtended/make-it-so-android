/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.example.makeitso.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makeitso.common.snackbar.SnackbarManager
import com.example.makeitso.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.makeitso.R.string as AppText
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
    private val crashlyticsService: CrashlyticsService
) : ViewModel() {
    var uiState = mutableStateOf(SettingsUiState())
        private set

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        SnackbarManager.showMessage(AppText.generic_error)
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(throwable) }
    }

    init {
        uiState.value = SettingsUiState(accountService.isAnonymousUser())
    }

    fun onSignOutClick(restartApp: () -> Unit) {
        viewModelScope.launch(exceptionHandler) {
            accountService.signOut()
            restartApp()
        }
    }

    fun onDeleteMyAccountClick(restartApp: () -> Unit) {
        viewModelScope.launch(exceptionHandler) {
            firestoreService.deleteAllForUser(accountService.getUserId()) { error ->
                if (error == null) deleteAccount(restartApp) else error.onError()
            }
        }
    }

    private fun deleteAccount(restartApp: () -> Unit) {
        viewModelScope.launch(exceptionHandler) {
            accountService.deleteAccount { error ->
                if (error == null) restartApp() else error.onError()
            }
        }
    }

    private fun Throwable.onError() {
        SnackbarManager.showMessage(this.toSnackbarMessage())
        crashlyticsService.logNonFatalCrash(this)
    }
}