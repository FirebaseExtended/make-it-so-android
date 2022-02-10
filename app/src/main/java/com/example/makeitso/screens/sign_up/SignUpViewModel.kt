package com.example.makeitso.screens.sign_up

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.makeitso.common.navigation.LOGIN_SCREEN
import com.example.makeitso.common.navigation.SIGN_UP_SCREEN
import com.example.makeitso.common.navigation.TASKS_SCREEN
import com.example.makeitso.model.database.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    fun onBackClick(navController: NavHostController) {
        navController.navigate(LOGIN_SCREEN) {
            popUpTo(SIGN_UP_SCREEN) { inclusive = true }
        }
    }

    fun onSignUpClick(navController: NavHostController) {
        //Apply sign up logic
        navController.navigate(TASKS_SCREEN) {
            popUpTo(SIGN_UP_SCREEN) { inclusive = true }
        }
    }

    fun onAnonymousSignUpClick(navController: NavHostController) {
        //Apply anonymous sign up logic
        navController.navigate(TASKS_SCREEN) {
            popUpTo(SIGN_UP_SCREEN) { inclusive = true }
        }
    }
}