package com.example.makeitso.model.service.impl

import com.example.makeitso.model.User
import com.example.makeitso.model.service.AccountService
import javax.inject.Inject

class AccountServiceImpl @Inject constructor() : AccountService {
    override suspend fun authenticate(email: String, password: String): User {
        return User(loginId = email)
        //Authenticate with Firebase
        //If something goes wrong, throw custom exception
    }

    override suspend fun createAccount(email: String, password: String): User {
        return User(loginId = email)
        //Create account with Firebase
        //If something goes wrong, throw custom exception
    }

    override suspend fun createAnonymousAccount(): User {
        return User(loginId = "")
        //Create anonymous account with Firebase
        //If something goes wrong, throw custom exception
    }
}