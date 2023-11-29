package com.example.lingualearn.words_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lingualearn.R
import com.example.lingualearn.data.Day
import com.example.lingualearn.data.DayDao
import com.example.lingualearn.data.LearnedWord
import com.example.lingualearn.utils.UiEventForUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class WordsScreenViewModel @Inject constructor(
    private val dayDao: DayDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiEvent = Channel<UiEventForUser>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var wordsList by mutableStateOf(emptyList<LearnedWord>())
        private set

    var day by mutableStateOf<Day?>(null)
        private set

    var esWord by mutableStateOf("")
        private set

    var uaWord by mutableStateOf("")
        private set


    init {
        val dayId = savedStateHandle.get<Int>("dayId")
        viewModelScope.launch {
            if (dayId != -1){
                day = dayDao.getDayById(dayId!!)
                wordsList = day?.words ?: emptyList()
            }
        }
    }

    fun onEvent(event: WordsScreenEvent){
        when(event){
            is WordsScreenEvent.OnAddNewWordClick -> {
                sendUiEvent(UiEventForUser.ShowDialog)
            }
            is WordsScreenEvent.OnEsWordChange -> {
                esWord = event.esWord
            }
            is WordsScreenEvent.OnSaveWord -> {
                if(uaWord != "" && esWord!= ""){
                    day?.let {
                        viewModelScope.launch {
                            val updatedWords = it.words.toMutableList()
                            updatedWords.add(LearnedWord(uaWord.trim(), esWord.trim()))
                            it.words = updatedWords
                            wordsList = updatedWords
                            dayDao.updateDay(it)
                        }
                    }
                    sendUiEvent(UiEventForUser.CloseDialog)
                    esWord = ""
                    uaWord = ""
                }
            }
            is WordsScreenEvent.OnTranslateClick -> {
                CoroutineScope(Dispatchers.IO).launch {
                    uaWord  = getTranslation(esWord)
                }
            }
        }
    }

    private fun getTranslation(text: String): String {
        val url = "https://api.mymemory.translated.net/get?q=${text}&langpair=es|uk"
        val request = Request.Builder()
            .url(url)
            .get()
            .addHeader("Content-Type", "application/json")
            .build()
        val client = OkHttpClient()
        val response = client.newCall(request).execute()
        return if (response.isSuccessful) {
            val responseBody = response.body?.string()
            val jsonResponse = responseBody?.let { JSONObject(it) }
            jsonResponse?.getJSONObject("responseData")?.getString("translatedText") ?: "translation error"
        } else {
            "Request failed: ${response.code}"
        }
    }

    private fun sendUiEvent(event: UiEventForUser){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}