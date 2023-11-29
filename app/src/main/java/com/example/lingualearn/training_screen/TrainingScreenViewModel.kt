package com.example.lingualearn.training_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lingualearn.R
import com.example.lingualearn.data.Day
import com.example.lingualearn.data.DayDao
import com.example.lingualearn.data.LearnedWord
import com.example.lingualearn.utils.Screen
import com.example.lingualearn.utils.UiEventForUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingScreenViewModel @Inject constructor(
    private val dao: DayDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var wordsList by mutableStateOf(emptyList<LearnedWord>())
        private set

    var uaWord by mutableStateOf("")
        private set

    var esWord by mutableStateOf("")
        private set

    var isAnswerCorrect by mutableStateOf(false)
        private set

    var isAnswerGiven by mutableStateOf(false)
        private set

    var isTrainingCompleted by mutableStateOf(false)
        private set

    var catDrawable by mutableIntStateOf(R.drawable.cat_long_words)
        private set

    var gainedXp by mutableIntStateOf(0)
        private set

    var day by mutableStateOf<Day?>(null)
        private set

    private val _uiEvent = Channel<UiEventForUser>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val dayId = savedStateHandle.get<Int>("dayId")
        viewModelScope.launch {
            if (dayId != -1){
                day = dao.getDayById(dayId!!)
            }
            dao.getAllDays().collect { days ->
                days.forEach { day ->
                    wordsList = wordsList + day.words
                }
                wordsList = wordsList.shuffled().take(10)
                esWord = wordsList[0].esWord
                isTrainingCompleted = false
                gainedXp = 0
                catDrawable = if (esWord.length < 8) R.drawable.cat_short_words_happy else R.drawable.cat_long_words
            }
        }
    }

    fun onEvent(event: TrainingScreenEvent) {
        val listSize = wordsList.size
        when (event) {
            is TrainingScreenEvent.OnCheckClick -> {
                isAnswerGiven = true
                if (uaWord.trim().equals(wordsList[0].uaWord, true)){
                    isAnswerCorrect = true
                    esWord = "Right :3"
                    gainedXp += 5
                } else {
                    esWord = "Wrong :/"
                    uaWord = "Correct answer: ${wordsList[0].uaWord}"
                }
            }
            is TrainingScreenEvent.OnUaWordChange -> {
                uaWord = event.uaWord
            }
            is TrainingScreenEvent.OnHintClick -> {
                uaWord.ifBlank {
                    uaWord = wordsList[0].uaWord[0].toString()
                }
            }
            is TrainingScreenEvent.OnContinueClick -> {
                isAnswerCorrect = false
                isAnswerGiven = false
                wordsList = wordsList.takeLast(listSize - 1)
                if (wordsList.isEmpty()) {
                    isTrainingCompleted = true
                } else {
                    esWord = wordsList[0].esWord
                    uaWord = ""
                    catDrawable = if (esWord.length < 8) R.drawable.cat_short_words_happy else R.drawable.cat_long_words
                }
            }
            is TrainingScreenEvent.OnTrainingCompleted -> {
                day?.let {
                    viewModelScope.launch {
                        it.xp += gainedXp
                        dao.updateDay(it)
                    }
                }
                sendUiEvent(UiEventForUser.Navigate(Screen.DaysScreen.route))
            }
        }
    }

    private fun sendUiEvent(event: UiEventForUser){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}