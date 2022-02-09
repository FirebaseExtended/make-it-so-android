package com.example.makeitso.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.makeitso.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM Task WHERE userId = :userId")
    fun selectAllForUser(userId: Long): Flow<List<Task>>

    @Query("SELECT * FROM Task WHERE id = :id")
    fun select(id: Long): Flow<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Query("DELETE FROM Task WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM Task WHERE userId = :userId")
    suspend fun deleteAllForUser(userId: Long)

    @Query("UPDATE Task SET completed = :isComplete WHERE id = :id")
    suspend fun updateCompletion(isComplete: Boolean, id: Long)

    @Query("UPDATE Task SET flag = :hasFlag WHERE id = :id")
    suspend fun updateFlag(hasFlag: Boolean, id: Long)
}
