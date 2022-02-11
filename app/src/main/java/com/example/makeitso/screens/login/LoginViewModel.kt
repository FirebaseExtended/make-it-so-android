package com.example.makeitso.screens.login

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.makeitso.common.navigation.LOGIN_SCREEN
import com.example.makeitso.common.navigation.SIGN_UP_SCREEN
import com.example.makeitso.model.database.repository.UserRepository
import com.example.makeitso.model.shared_prefs.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPrefs: SharedPrefs
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiModel())
    val uiState: StateFlow<LoginUiModel> = _uiState

    fun onSignInClick(navController: NavHostController) {
        //Apply login logic (Call Auth and update SharedPrefs)
        //Show error if needed by running:
        _uiState.value = LoginUiModel(hasError = true)

//        navController.navigate(TASKS_SCREEN) {
//            popUpTo(LOGIN_SCREEN) { inclusive = true }
//        }
    }

    fun onSignUpClick(navController: NavHostController) {
        navController.navigate(SIGN_UP_SCREEN) {
            popUpTo(LOGIN_SCREEN) { inclusive = true }
        }
    }
}