package com.example.makeitso.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.makeitso.common.navigation.LOGIN_SCREEN
import com.example.makeitso.common.navigation.SIGN_UP_SCREEN
import com.example.makeitso.model.database.repository.UserRepository
import com.example.makeitso.model.shared_prefs.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPrefs: SharedPrefs
) : ViewModel() {
    var uiState = mutableStateOf<LoginUiState>(LoginUiState.InitialState)
        private set

    fun onSignInClick(navController: NavHostController, email: String, password: String) {
        //uiState.value = LoginUiState.ErrorState
        //Apply login logic (Call Auth and update SharedPrefs)
        //Show error if needed by running:

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