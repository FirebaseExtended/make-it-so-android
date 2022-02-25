package com.example.makeitso.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.makeitso.model.Task

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Query("SELECT * FROM Task WHERE id = :id")
    suspend fun getById(id: String): Task

    @Query("SELECT * FROM Task WHERE userId = :userId")
    suspend fun getAllForUser(userId: String): List<Task>

    @Query("DELETE FROM Task WHERE id = :id")
    suspend fun delete(id: String)

    @Query("UPDATE Task SET completed = :isComplete WHERE id = :id")
    suspend fun updateCompletion(isComplete: Boolean, id: String)

    @Query("UPDATE Task SET flag = :hasFlag WHERE id = :id")
    suspend fun updateFlag(hasFlag: Boolean, id: String)
}
