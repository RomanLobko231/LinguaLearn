package com.example.lingualearn.data

import androidx.room.Entity
import com.example.lingualearn.utils.LearnedWordSerializer
import kotlinx.serialization.Serializable

@Serializable(with = LearnedWordSerializer::class)
data class LearnedWord(
    val uaWord: String,
    val esWord: String
)
