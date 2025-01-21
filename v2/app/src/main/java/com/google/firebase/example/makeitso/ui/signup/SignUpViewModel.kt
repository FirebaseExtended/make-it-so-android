package com.google.firebase.example.makeitso.ui.signup

import com.google.firebase.example.makeitso.MainViewModel
import com.google.firebase.example.makeitso.R
import com.google.firebase.example.makeitso.data.model.ErrorMessage
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
        showErrorSnackbar: (ErrorMessage) -> Unit
    ) {
        if (!email.isValidEmail()) {
            showErrorSnackbar(ErrorMessage.IdError(R.string.invalid_email))
            return
        }

        if (!password.isValidPassword()) {
            showErrorSnackbar(ErrorMessage.IdError(R.string.invalid_password))
            return
        }

        if (password != repeatPassword) {
            showErrorSnackbar(ErrorMessage.IdError(R.string.passwords_do_not_match))
            return
        }

        launchCatching(showErrorSnackbar) {
            authRepository.signUp(email, password)
            openHomeScreen()
        }
    }
}