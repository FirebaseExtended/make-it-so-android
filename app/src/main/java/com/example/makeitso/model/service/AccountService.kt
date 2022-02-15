package com.example.makeitso.model.service

import com.example.makeitso.model.User

interface AccountService {
    suspend fun authenticate(email: String, password: String): User
    suspend fun createAccount(email: String, password: String): User
    suspend fun createAnonymousAccount(): User
}
