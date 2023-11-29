package com.example.lingualearn.days_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lingualearn.data.Day
import com.example.lingualearn.data.DayDao
import com.example.lingualearn.utils.Screen
import com.example.lingualearn.utils.UiEventForUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DaysScreenViewModel @Inject constructor(
    private val dao: DayDao
) : ViewModel() {

    val daysList = dao.getAllDays()

    var lastDate by mutableStateOf<LocalDate?>(null)
        private set

    var currentDayId by mutableIntStateOf(0)
        private set

    @OptIn(FlowPreview::class)
    val totalWordsCount: Flow<Int> = daysList.flatMapConcat { days ->
        flowOf(days.sumOf { it.words.size })
    }

    @OptIn(FlowPreview::class)
    val totalXpCount: Flow<Int> = daysList.flatMapConcat { days ->
        flowOf(days.sumOf { it.xp })
    }

    var lastDay by mutableStateOf<Day?>(null)
        private set


    init {
        viewModelScope.launch {
            lastDay = dao.getLastDay()
            lastDate = getLastActiveDate()
            currentDayId = getCurrDay()
        }
    }

    private val _uiEvent = Channel<UiEventForUser>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: DaysScreenEvent) {
        when (event) {
            is DaysScreenEvent.OnStartNewDayClick -> {
                viewModelScope.launch {
                    dao.insertDay(Day(words = emptyList()))
                    lastDay = dao.getLastDay()
                    currentDayId = getCurrDay()
                    lastDate = getLastActiveDate()

                }
            }

            is DaysScreenEvent.OnDayClick -> {
                sendUiEvent(UiEventForUser.Navigate(Screen.WordsScreen.route + "?dayId=${event.day.id}"))
            }

            DaysScreenEvent.OnTrainingClick -> {
                sendUiEvent(UiEventForUser.Navigate(Screen.TrainingScreen.route + "?dayId=${lastDay!!.id}"))
            }
        }
    }

    private fun getLastActiveDate(): LocalDate? = lastDay?.date?.let { LocalDate.parse(it) }


    private fun getCurrDay(): Int = lastDay?.id ?: 0


    private fun sendUiEvent(event: UiEventForUser) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}