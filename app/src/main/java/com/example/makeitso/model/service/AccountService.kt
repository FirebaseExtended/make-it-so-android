package com.example.makeitso.model.service

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AccountService {
    fun hasUser(): Boolean
    fun isAnonymousUser(): Boolean
    fun getUserId(): String
    fun getAnonymousUserId(): String
    fun authenticate(email: String, password: String, callback: (Task<AuthResult>) -> Unit)
    fun createAccount(email: String, password: String, callback: (Task<AuthResult>) -> Unit)
    fun sendRecoveryEmail(email: String, callback: (Throwable?) -> Unit)
    fun createAnonymousAccount(callback: (Task<AuthResult>) -> Unit)
    fun linkAccount(email: String, password: String, callback: () -> Unit)
    fun deleteAccount(callback: (Throwable?) -> Unit)
    fun signOut()
}
