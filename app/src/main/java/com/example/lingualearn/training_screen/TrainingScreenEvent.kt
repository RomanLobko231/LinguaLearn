package com.example.lingualearn.training_screen

sealed class TrainingScreenEvent{
    object OnCheckClick: TrainingScreenEvent()
    data class OnUaWordChange(val uaWord: String): TrainingScreenEvent()
    object OnHintClick: TrainingScreenEvent()
    object OnContinueClick: TrainingScreenEvent()
    object OnTrainingCompleted: TrainingScreenEvent()
}
