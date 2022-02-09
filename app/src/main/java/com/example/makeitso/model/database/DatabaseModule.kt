package com.example.makeitso.model.database

import android.content.Context
import androidx.room.Room
import com.example.makeitso.model.database.dao.TaskDao
import com.example.makeitso.model.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideTaskDao(database: MakeItSoDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    fun provideUserDao(database: MakeItSoDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): MakeItSoDatabase {
        return Room.databaseBuilder(
            appContext,
            MakeItSoDatabase::class.java,
            DB_NAME
        ).build()
    }

    companion object {
        private const val DB_NAME = "make_it_so_db"
    }
}