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

    @Query("DELETE FROM Task WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("UPDATE Task SET completed = :isComplete WHERE id = :id")
    suspend fun updateCompletion(isComplete: Boolean, id: Long)

    @Query("UPDATE Task SET flag = :hasFlag WHERE id = :id")
    suspend fun updateFlag(hasFlag: Boolean, id: Long)
}
