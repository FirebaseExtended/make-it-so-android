package com.example.makeitso.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.makeitso.model.Task
import com.example.makeitso.model.User
import com.example.makeitso.model.database.dao.TaskDao
import com.example.makeitso.model.database.dao.UserDao

@Database(entities = [Task::class, User::class], version = 1, exportSchema = false)
abstract class MakeItSoDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun userDao(): UserDao
}
