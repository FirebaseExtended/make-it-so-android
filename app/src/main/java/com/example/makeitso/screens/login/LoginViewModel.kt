package com.example.makeitso.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.error.ErrorMessage
import com.example.makeitso.common.error.ErrorMessage.Companion.toErrorMessage
import com.example.makeitso.common.error.ErrorMessage.ResourceError
import com.example.makeitso.common.ext.isValidEmail
import com.example.makeitso.common.ext.isValidPassword
import com.example.makeitso.common.navigation.LOGIN_SCREEN
import com.example.makeitso.common.navigation.SIGN_UP_SCREEN
import com.example.makeitso.common.navigation.TASKS_SCREEN
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.service.CrashlyticsService
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
    private val crashlyticsService: CrashlyticsService
) : ViewModel() {
    var uiState = mutableStateOf(LoginUiState())
        private set

    val snackbarChannel = Channel<ErrorMessage>(Channel.CONFLATED)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        snackbarChannel.trySend(ResourceError(AppText.generic_error))
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(throwable) }
    }

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignInClick(navController: NavHostController) {
        if (!uiState.value.email.isValidEmail()) {
            snackbarChannel.trySend(ResourceError(AppText.email_error))
            return
        }

        if (uiState.value.password.isBlank()) {
            snackbarChannel.trySend(ResourceError(AppText.empty_password_error))
            return
        }

        viewModelScope.launch(exceptionHandler) {
            accountService.authenticate(uiState.value.email, uiState.value.password) { task ->
                task.onResult(navController)
            }
        }
    }

    fun onSignUpClick(navController: NavHostController) {
        navController.navigate(SIGN_UP_SCREEN) {
            popUpTo(LOGIN_SCREEN) { inclusive = true }
        }
    }

    private fun Task<AuthResult>.onResult(navController: NavHostController) {
        if (this.isSuccessful) {
            navController.navigate(TASKS_SCREEN) {
                popUpTo(LOGIN_SCREEN) { inclusive = true }
            }
        } else {
            snackbarChannel.trySend(this.exception.toErrorMessage())
            crashlyticsService.logNonFatalCrash(this.exception)
        }
    }
}