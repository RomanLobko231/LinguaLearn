package com.example.lingualearn

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lingualearn.data.DayDao
import com.example.lingualearn.data.LearnedWord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Assert.*
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest() {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

@HiltViewModel
class TrainingScreenViewModel @Inject constructor(
    private val dao: DayDao
): ViewModel()  {
    var wordsList by mutableStateOf(emptyList<LearnedWord>())
        private set
    @Test
    fun getWords(){
        viewModelScope.launch {
            wordsList = dao.getAllWords().flatten()
            wordsList.forEach {
                println(it)
            }
        }
    }
}