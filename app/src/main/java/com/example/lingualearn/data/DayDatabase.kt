package com.example.lingualearn.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lingualearn.utils.Converters

@Database(
    entities = [Day::class],
    version = 3
)
@TypeConverters(Converters::class)
abstract class DayDatabase: RoomDatabase() {
    abstract val dao: DayDao
}