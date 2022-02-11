package com.example.makeitso.screens.splash

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.makeitso.common.navigation.LOGIN_SCREEN
import com.example.makeitso.common.navigation.SPLASH_SCREEN
import com.example.makeitso.common.navigation.TASKS_SCREEN
import com.example.makeitso.model.shared_prefs.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharedPrefs: SharedPrefs
) : ViewModel() {
    fun onAppStart(navController: NavHostController) {
        val nextScreen = if (sharedPrefs.hasUser()) TASKS_SCREEN else LOGIN_SCREEN

        navController.navigate(nextScreen) {
            popUpTo(SPLASH_SCREEN) { inclusive = true }
        }
    }
}