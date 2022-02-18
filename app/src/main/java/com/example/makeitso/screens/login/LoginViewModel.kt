package com.example.makeitso.screens.login

import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.navigation.LOGIN_SCREEN
import com.example.makeitso.common.navigation.SIGN_UP_SCREEN
import com.example.makeitso.common.navigation.TASKS_SCREEN
import com.example.makeitso.model.database.repository.UserRepository
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.service.CrashlyticsService
import com.example.makeitso.model.shared_prefs.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
    private val crashlyticsService: CrashlyticsService,
    private val userRepository: UserRepository,
    private val sharedPrefs: SharedPrefs
) : ViewModel() {
    var uiState = mutableStateOf(LoginUiState())
        private set

    val snackbarChannel = Channel<@StringRes Int>(Channel.CONFLATED)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        snackbarChannel.trySend(AppText.login_error)
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(throwable) }
    }

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignInClick(navController: NavHostController) {
        viewModelScope.launch(exceptionHandler) {
            val user = accountService.authenticate(uiState.value.email, uiState.value.password)

            userRepository.insert(user)
            sharedPrefs.saveCurrentUserId(user.id)

            navController.navigate(TASKS_SCREEN) {
                popUpTo(LOGIN_SCREEN) { inclusive = true }
            }
        }
    }

    fun onSignUpClick(navController: NavHostController) {
        navController.navigate(SIGN_UP_SCREEN) {
            popUpTo(LOGIN_SCREEN) { inclusive = true }
        }
    }
}