package com.example.makeitso.model.database.repository

import com.example.makeitso.model.User
import com.example.makeitso.model.database.dao.UserDao
import kotlinx.coroutines.Dispatchers

class UserRepo(private val userDao: UserDao) {
    suspend fun insert(user: User) {
        Dispatchers.IO.apply { userDao.insert(user) }
    }

    suspend fun delete(user: User) {
        Dispatchers.IO.apply { userDao.delete(user.id) }
    }
}
