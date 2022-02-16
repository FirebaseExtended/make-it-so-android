package com.example.makeitso.model.database.repository

import com.example.makeitso.model.User

interface UserRepository {
    suspend fun insert(user: User)
    suspend fun delete(userId: Long)
}
