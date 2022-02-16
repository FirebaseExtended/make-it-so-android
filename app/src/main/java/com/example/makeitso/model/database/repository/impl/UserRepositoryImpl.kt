package com.example.makeitso.model.database.repository.impl

import com.example.makeitso.model.User
import com.example.makeitso.model.database.dao.UserDao
import com.example.makeitso.model.database.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDao: UserDao) : UserRepository {
    override suspend fun insert(user: User) {
        Dispatchers.IO.apply { userDao.insert(user) }
    }

    override suspend fun delete(userId: Long) {
        Dispatchers.IO.apply { userDao.delete(userId) }
    }
}
