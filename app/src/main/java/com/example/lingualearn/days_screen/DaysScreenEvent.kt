package com.example.lingualearn.days_screen

import com.example.lingualearn.data.Day

sealed class DaysScreenEvent{
    data class OnDayClick(val day: Day): DaysScreenEvent()
    object OnStartNewDayClick: DaysScreenEvent()
    object OnTrainingClick: DaysScreenEvent()
}
