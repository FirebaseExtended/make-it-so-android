package com.example.makeitso.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.makeitso.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
   @Query("SELECT * FROM User WHERE id = :id")
    fun select(id: String): Flow<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("DELETE FROM User WHERE id = :id")
    suspend fun delete(id: String)
}
