package com.google.firebase.example.makeitso.ui.signup

import com.google.firebase.example.makeitso.MainViewModel
import com.google.firebase.example.makeitso.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : MainViewModel()