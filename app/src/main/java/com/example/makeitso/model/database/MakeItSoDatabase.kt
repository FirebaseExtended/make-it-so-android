package com.example.makeitso.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.makeitso.model.Task
import com.example.makeitso.model.User
import com.example.makeitso.model.database.dao.TaskDao
import com.example.makeitso.model.database.dao.UserDao

@Database(entities = [Task::class, User::class], version = 1, exportSchema = false)
abstract class MakeItSoDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: MakeItSoDatabase? = null

        private const val DB_NAME = "make_it_so_db"

        fun getDatabase(context: Context): MakeItSoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MakeItSoDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
