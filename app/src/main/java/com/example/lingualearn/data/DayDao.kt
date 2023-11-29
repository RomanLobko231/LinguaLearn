package com.example.lingualearn.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDay(day: Day)

    @Query("SELECT * FROM day ORDER BY id DESC")
    fun getAllDays(): Flow<List<Day>>

    @Query("SELECT * FROM day ORDER BY id DESC LIMIT 1")
    suspend fun getLastDay(): Day?

    @Query("SELECT * FROM day WHERE id = :id")
    suspend fun getDayById(id: Int): Day?

    @Update
    suspend fun updateDay(day: Day)

}