package com.google.firebase.example.makeitso.ui.signin

import com.google.firebase.example.makeitso.MainViewModel
import com.google.firebase.example.makeitso.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : MainViewModel() {
    fun signIn(email: String, password: String, openHomeScreen: () -> Unit) {
        launchCatching {
            authRepository.signIn(email, password)
            openHomeScreen()
        }
    }
}