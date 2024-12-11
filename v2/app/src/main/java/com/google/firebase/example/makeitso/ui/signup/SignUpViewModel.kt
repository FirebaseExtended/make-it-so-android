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
        openHomeScreen: () -> Unit,
        showErrorSnackbar: (String) -> Unit
    ) {
        if (!email.isValidEmail()) {
            showErrorSnackbar(INVALID_EMAIL_ERROR)
            return
        }

        if (!password.isValidPassword()) {
            showErrorSnackbar(INVALID_PASSWORD_ERROR)
            return
        }

        if (password != repeatPassword) {
            showErrorSnackbar(PASSWORDS_DO_NOT_MATCH_ERROR)
            return
        }

        launchCatching(showErrorSnackbar) {
            authRepository.signUp(email, password)
            openHomeScreen()
        }
    }

    companion object {
        private const val INVALID_EMAIL_ERROR = "Invalid email format"
        private const val INVALID_PASSWORD_ERROR = "Passwords should have at least eight digits and include one digit, one lower case letter and one upper case letter"
        private const val PASSWORDS_DO_NOT_MATCH_ERROR = "Passwords do not match"
    }
}