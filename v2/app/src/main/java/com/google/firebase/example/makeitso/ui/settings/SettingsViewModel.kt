package com.google.firebase.example.makeitso.ui.settings

import com.google.firebase.example.makeitso.MainViewModel
import com.google.firebase.example.makeitso.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : MainViewModel() {
    private val _shouldRestartApp = MutableStateFlow(false)
    val shouldRestartApp: StateFlow<Boolean>
        get() = _shouldRestartApp.asStateFlow()

    private val _isAnonymous = MutableStateFlow(true)
    val isAnonymous: StateFlow<Boolean>
        get() = _isAnonymous.asStateFlow()

    fun loadCurrentUser() {
        launchCatching {
            val currentUser = authRepository.currentUser
            _isAnonymous.value = currentUser != null && currentUser.isAnonymous
        }
    }

    fun signOut() {
        launchCatching {
            authRepository.signOut()
            _shouldRestartApp.value = true
        }
    }

    fun deleteAccount() {
        launchCatching {
            authRepository.deleteAccount()
            _shouldRestartApp.value = true
        }
    }
}