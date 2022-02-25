package com.example.makeitso.screens.sign_up

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.makeitso.common.error.ErrorMessage
import com.example.makeitso.common.error.ErrorMessage.Companion.toErrorMessage
import com.example.makeitso.common.navigation.LOGIN_SCREEN
import com.example.makeitso.common.navigation.SIGN_UP_SCREEN
import com.example.makeitso.common.navigation.TASKS_SCREEN
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.service.CrashlyticsService
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    private val crashlyticsService: CrashlyticsService
) : ViewModel() {
    var uiState = mutableStateOf(SignUpUiState())
        private set

    val snackbarChannel = Channel<ErrorMessage>(Channel.CONFLATED)

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignUpClick(navController: NavHostController) {
        accountService.createAccount(uiState.value.email, uiState.value.password) { task ->
            task.onResult(navController)
        }
    }

    fun onAnonymousSignUpClick(navController: NavHostController) {
        accountService.createAnonymousAccount { task ->
            task.onResult(navController)
        }
    }

    fun onBackClick(navController: NavHostController) {
        navController.navigate(LOGIN_SCREEN) {
            popUpTo(SIGN_UP_SCREEN) { inclusive = true }
        }
    }

    private fun Task<AuthResult>.onResult(navController: NavHostController) {
        if (this.isSuccessful) {
            navController.navigate(TASKS_SCREEN) {
                popUpTo(SIGN_UP_SCREEN) { inclusive = true }
            }
        } else {
            crashlyticsService.logNonFatalCrash(this.exception)
            snackbarChannel.trySend(this.exception.toErrorMessage())
        }
    }
}