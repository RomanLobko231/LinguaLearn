package com.example.lingualearn.utils

sealed class UiEventForUser{
    data class Navigate(val route: String): UiEventForUser()
    object PopBackStack: UiEventForUser()
    object ShowDialog: UiEventForUser()
    object CloseDialog: UiEventForUser()
    object UpdateTrainingWord: UiEventForUser()
}
