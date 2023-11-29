package com.example.lingualearn.utils

import androidx.room.TypeConverter
import com.example.lingualearn.data.LearnedWord
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDate

class Converters {

    @TypeConverter
    fun fromList(value: List<LearnedWord>) = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<LearnedWord>>(value)

}