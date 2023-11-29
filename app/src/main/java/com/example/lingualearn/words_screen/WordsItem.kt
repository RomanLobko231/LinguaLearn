package com.example.lingualearn.words_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lingualearn.data.LearnedWord

@Composable
fun WordsItem(
    learnedWord: LearnedWord
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp, 8.dp, 12.dp, 0.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),

    ) {
        Text(
            text = learnedWord.esWord,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = learnedWord.uaWord,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Divider(modifier = Modifier.fillMaxWidth())
        }

    }
}