package com.example.makeitso.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.makeitso.model.Task
import com.example.makeitso.model.database.dao.TaskDao

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class MakeItSoDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
