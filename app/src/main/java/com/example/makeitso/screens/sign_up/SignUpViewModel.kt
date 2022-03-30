package com.example.makeitso.screens.sign_up

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.makeitso.common.ext.isValidEmail
import com.example.makeitso.common.ext.isValidPassword
import com.example.makeitso.common.ext.passwordMatches
import com.example.makeitso.common.snackbar.SnackbarManager
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.service.CrashlyticsService
import com.example.makeitso.model.service.FirestoreService
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val crashlyticsService: CrashlyticsService
) : ViewModel() {
    var uiState = mutableStateOf(SignUpUiState())
        private set

    private val email get() = uiState.value.email
    private val password get() = uiState.value.password

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        SnackbarManager.showMessage(AppText.generic_error)
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(throwable) }
    }

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

        viewModelScope.launch(exceptionHandler) {
            accountService.createAccount(email, password) { task ->
                task.onResult { linkWithEmail(restartApp) }
            }
        }
    }

    private fun linkWithEmail(restartApp: () -> Unit) {
        viewModelScope.launch(exceptionHandler) {
            accountService.linkAccount(email, password) { updateUserId(restartApp) }
        }
    }

    private fun updateUserId(restartApp: () -> Unit) {
        viewModelScope.launch(exceptionHandler) {
            val oldUserId = accountService.getAnonymousUserId()
            val newUserId = accountService.getUserId()

            firestoreService.updateUserId(oldUserId, newUserId) { error ->
                if (error == null) restartApp()
                else viewModelScope.launch { crashlyticsService.logNonFatalCrash(error) }
            }
        }
    }

    private fun Task<AuthResult>.onResult(successCallback: () -> Unit) {
        if (this.isSuccessful) {
            successCallback()
        } else {
            SnackbarManager.showMessage(this.exception.toSnackbarMessage())
            crashlyticsService.logNonFatalCrash(this.exception)
        }
    }
}