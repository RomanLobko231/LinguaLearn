package com.example.lingualearn.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lingualearn.utils.LearnedWordSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Entity
data class Day(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
    var xp: Int = 0,
    @Serializable(with = LearnedWordSerializer::class) var words: List<LearnedWord> = emptyList()
)
