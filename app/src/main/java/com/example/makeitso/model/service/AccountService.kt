package com.example.makeitso.model.service

interface AccountService {
    suspend fun authenticate(email: String, password: String)
    suspend fun createAccount(email: String, password: String)
    suspend fun createAnonymousAccount()
    suspend fun signOut()
    suspend fun getUserId(): String
}
