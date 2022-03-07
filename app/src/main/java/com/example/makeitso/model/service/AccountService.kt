package com.example.makeitso.model.service

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AccountService {
    fun hasUser(): Boolean
    fun authenticate(email: String, password: String, callback: (Task<AuthResult>) -> Unit)
    fun createAccount(email: String, password: String, callback: (Task<AuthResult>) -> Unit)
    fun sendRecoveryEmail(email: String, callback: (Throwable?) -> Unit)
    fun createAnonymousAccount(callback: (Task<AuthResult>) -> Unit)
    fun signOut()
    fun getUserId(): String
}
