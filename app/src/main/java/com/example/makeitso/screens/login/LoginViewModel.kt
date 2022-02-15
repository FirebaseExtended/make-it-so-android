package com.example.makeitso.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.makeitso.common.navigation.LOGIN_SCREEN
import com.example.makeitso.common.navigation.SIGN_UP_SCREEN
import com.example.makeitso.common.navigation.TASKS_SCREEN
import com.example.makeitso.model.database.repository.UserRepository
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.service.CrashlyticsService
import com.example.makeitso.model.shared_prefs.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
    private val crashlyticsService: CrashlyticsService,
    private val userRepository: UserRepository,
    private val sharedPrefs: SharedPrefs
) : ViewModel() {
    var uiState = mutableStateOf<LoginUiState>(LoginUiState.InitialState)
        private set

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        uiState.value = LoginUiState.ErrorState
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(throwable) }
    }

    fun onSignInClick(navController: NavHostController, email: String, password: String) {
        uiState.value = LoginUiState.InitialState

        viewModelScope.launch(exceptionHandler) {
            val user = accountService.authenticate(email, password)

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