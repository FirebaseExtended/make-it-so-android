package com.google.firebase.example.makeitso.data.repository

import com.google.firebase.example.makeitso.data.datasource.AuthRemoteDataSource
import com.google.firebase.example.makeitso.data.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) {
    val currentUser: Flow<User> = authRemoteDataSource.currentUser

    suspend fun createGuestAccount() {
        authRemoteDataSource.createGuestAccount()
    }

    suspend fun signIn(email: String, password: String) {
        authRemoteDataSource.signIn(email, password)
    }

    suspend fun signUp(email: String, password: String) {
       authRemoteDataSource.linkAccount(email, password)
    }

    suspend fun signOut() {
        authRemoteDataSource.signOut()
        authRemoteDataSource.createGuestAccount()
    }
}