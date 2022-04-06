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
import androidx.lifecycle.viewModelScope
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.service.LogService
import com.example.makeitso.model.service.StorageService
import com.example.makeitso.screens.MakeItSoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val storageService: StorageService
) : MakeItSoViewModel(logService) {
    var uiState = mutableStateOf(SettingsUiState())
        private set

    fun initialize() {
        uiState.value = SettingsUiState(accountService.isAnonymousUser())
    }

    fun onSignOutClick(restartApp: () -> Unit) {
        viewModelScope.launch(showErrorExceptionHandler) {
            accountService.signOut()
            restartApp()
        }
    }

    fun onDeleteMyAccountClick(restartApp: () -> Unit) {
        viewModelScope.launch(showErrorExceptionHandler) {
            storageService.deleteAllForUser(accountService.getUserId()) { error ->
                if (error == null) deleteAccount(restartApp) else onError(error)
            }
        }
    }

    private fun deleteAccount(restartApp: () -> Unit) {
        viewModelScope.launch(showErrorExceptionHandler) {
            accountService.deleteAccount { error ->
                if (error == null) restartApp() else onError(error)
            }
        }
    }
}