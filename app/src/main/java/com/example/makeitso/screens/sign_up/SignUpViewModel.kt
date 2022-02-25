package com.example.makeitso.screens.sign_up

import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.navigation.LOGIN_SCREEN
import com.example.makeitso.common.navigation.SIGN_UP_SCREEN
import com.example.makeitso.common.navigation.TASKS_SCREEN
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.service.CrashlyticsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    private val crashlyticsService: CrashlyticsService
) : ViewModel(), Executor {
    var uiState = mutableStateOf(SignUpUiState())
        private set

    val snackbarChannel = Channel<@StringRes Int>(Channel.CONFLATED)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        snackbarChannel.trySend(AppText.generic_error)
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(throwable) }
    }

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignUpClick(navController: NavHostController) {
        viewModelScope.launch(exceptionHandler) {
            accountService.createAccount(uiState.value.email, uiState.value.password)
//            userRepository.insert(user)
//            sharedPrefs.saveCurrentUserId(user.id)
        }
    }

    fun onAnonymousSignUpClick(navController: NavHostController) {
        viewModelScope.launch(exceptionHandler) {
            val user = accountService.createAnonymousAccount()

          //  userRepository.insert(user)
          //  sharedPrefs.saveCurrentUserId(user.id)

            navController.navigate(TASKS_SCREEN) {
                popUpTo(SIGN_UP_SCREEN) { inclusive = true }
            }
        }
    }

    fun onBackClick(navController: NavHostController) {
        navController.navigate(LOGIN_SCREEN) {
            popUpTo(SIGN_UP_SCREEN) { inclusive = true }
        }
    }

    override fun execute(p0: Runnable?) {
        TODO("Not yet implemented")
    }
}