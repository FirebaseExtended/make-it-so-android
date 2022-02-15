package com.example.makeitso.screens.sign_up

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.makeitso.common.navigation.LOGIN_SCREEN
import com.example.makeitso.common.navigation.SIGN_UP_SCREEN
import com.example.makeitso.common.navigation.TASKS_SCREEN
import com.example.makeitso.model.database.repository.UserRepository
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.shared_prefs.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    private val userRepository: UserRepository,
    private val sharedPrefs: SharedPrefs
) : ViewModel() {
    var uiState = mutableStateOf<SignUpUiState>(SignUpUiState.InitialState)
        private set

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        uiState.value = SignUpUiState.ErrorState
        //Log non fatal crash to Firebase
    }

    fun onBackClick(navController: NavHostController) {
        navController.navigate(LOGIN_SCREEN) {
            popUpTo(SIGN_UP_SCREEN) { inclusive = true }
        }
    }

    fun onSignUpClick(navController: NavHostController, email: String, password: String) {
        uiState.value = SignUpUiState.InitialState

        viewModelScope.launch(exceptionHandler) {
            val user = accountService.createAccount(email, password)

            userRepository.insert(user)
            sharedPrefs.saveCurrentUserId(user.id)

            navController.navigate(TASKS_SCREEN) {
                popUpTo(SIGN_UP_SCREEN) { inclusive = true }
            }
        }
    }

    fun onAnonymousSignUpClick(navController: NavHostController) {
        uiState.value = SignUpUiState.InitialState

        viewModelScope.launch(exceptionHandler) {
            val user = accountService.createAnonymousAccount()

            userRepository.insert(user)
            sharedPrefs.saveCurrentUserId(user.id)

            navController.navigate(TASKS_SCREEN) {
                popUpTo(SIGN_UP_SCREEN) { inclusive = true }
            }
        }
    }
}