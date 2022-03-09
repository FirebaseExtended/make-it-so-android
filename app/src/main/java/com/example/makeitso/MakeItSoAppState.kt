package com.example.makeitso

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController

@Stable
class MakeItSoAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController
) {
    fun popUp() {
        navController.popBackStack()
    }

    fun navigate(route: String) {
        navController.navigate(route)
    }

    fun popUpAndNavigate(route: String, popUp: String) {
        navController.navigate(route) {
            popUpTo(popUp) { inclusive = true }
        }
    }

    fun clearAndNavigate(route: String) {
        navController.navigate(route) {
            popUpTo(0) { inclusive = true }
        }
    }
}