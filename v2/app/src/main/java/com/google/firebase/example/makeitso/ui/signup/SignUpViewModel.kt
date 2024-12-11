package com.google.firebase.example.makeitso.ui.signup

import com.google.firebase.example.makeitso.MainViewModel
import com.google.firebase.example.makeitso.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : MainViewModel() {
    fun signUp(
        email: String,
        password: String,
        repeatPassword: String,
        openHomeScreen: () -> Unit
    ) {
        if (!email.isValidEmail()) {
            //TODO: Launch error snackbar
            return
        }

        if (!password.isValidPassword()) {
            //TODO: Launch error snackbar
            return
        }

        if (password != repeatPassword) {
            //TODO: Launch error snackbar
            return
        }

        launchCatching {
            authRepository.signUp(email, password)
            openHomeScreen()
        }
    }
}