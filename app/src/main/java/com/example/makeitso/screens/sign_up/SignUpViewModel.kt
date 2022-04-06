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

package com.example.makeitso.screens.sign_up

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.ext.isValidEmail
import com.example.makeitso.common.ext.isValidPassword
import com.example.makeitso.common.ext.passwordMatches
import com.example.makeitso.common.snackbar.SnackbarManager
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.service.CrashlyticsService
import com.example.makeitso.model.service.FirestoreService
import com.example.makeitso.screens.MakeItSoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val crashlyticsService: CrashlyticsService
) : MakeItSoViewModel(crashlyticsService) {
    var uiState = mutableStateOf(SignUpUiState())
        private set

    private val email get() = uiState.value.email
    private val password get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onSignUpClick(restartApp: () -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            SnackbarManager.showMessage(AppText.password_match_error)
            return
        }

        viewModelScope.launch(showErrorExceptionHandler) {
            accountService.createAccount(email, password) { task ->
                if (task.isSuccessful) linkWithEmail(restartApp) else onError(task.exception)
            }
        }
    }

    private fun linkWithEmail(restartApp: () -> Unit) {
        viewModelScope.launch(showErrorExceptionHandler) {
            accountService.linkAccount(email, password) { updateUserId(restartApp) }
        }
    }

    private fun updateUserId(restartApp: () -> Unit) {
        viewModelScope.launch(showErrorExceptionHandler) {
            val oldUserId = accountService.getAnonymousUserId()
            val newUserId = accountService.getUserId()

            firestoreService.updateUserId(oldUserId, newUserId) { error ->
                if (error == null) restartApp()
                else viewModelScope.launch { crashlyticsService.logNonFatalCrash(error) }
            }
        }
    }
}