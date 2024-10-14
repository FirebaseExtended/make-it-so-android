package com.google.firebase.example.makeitso.data.repository

import com.google.firebase.example.makeitso.data.datasource.AuthRemoteDataSource
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) {

    //val data: Flow<User> = authRemoteDataSource.user
}