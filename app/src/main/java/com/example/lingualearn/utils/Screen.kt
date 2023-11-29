package com.example.lingualearn.utils

sealed class Screen(val route: String){
    object DaysScreen: Screen("days_screen")
    object WordsScreen: Screen("words_screen")
    object TrainingScreen: Screen("training_screen")
}
