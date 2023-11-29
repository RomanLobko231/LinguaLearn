package com.example.lingualearn.words_screen

sealed class WordsScreenEvent{
    object OnAddNewWordClick: WordsScreenEvent()
    object OnTranslateClick: WordsScreenEvent()
    data class OnEsWordChange(val esWord: String): WordsScreenEvent()
    data class OnSaveWord(val esWord: String, val uaWord: String) : WordsScreenEvent()
}
