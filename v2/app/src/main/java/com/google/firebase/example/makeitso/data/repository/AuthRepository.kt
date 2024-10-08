package com.google.firebase.example.makeitso.data.repository

import com.google.firebase.example.makeitso.data.datasource.AuthRemoteDataSource

class AuthRepository(
    private val authRemoteDataSource: AuthRemoteDataSource
) {

    //val data: Flow<User> = authRemoteDataSource.user
}