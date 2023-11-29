package com.example.lingualearn.training_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lingualearn.R
import com.example.lingualearn.utils.UiEventForUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingScreen(
    viewModel: TrainingScreenViewModel = hiltViewModel(),
    onNavigate: (UiEventForUser.Navigate) -> Unit
) {

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event){
                is UiEventForUser.Navigate -> {
                    onNavigate(event)
                }
                else -> {}
            }
        }
    }
    if(!viewModel.isTrainingCompleted){
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier.clip(RoundedCornerShape(28.dp, 28.dp, 0.dp, 0.dp)),
                    actions = {
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(imageVector = Icons.Filled.Star, contentDescription = "get a hint")
                        Text(
                            text = "Hint",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .clickable {
                                    viewModel.onEvent(TrainingScreenEvent.OnHintClick)
                                }
                                .weight(1f)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    },
                    floatingActionButton = {
                        if (viewModel.isAnswerGiven){
                            ExtendedFloatingActionButton(
                                text = {
                                    Text(
                                        text = "Continue",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                },
                                icon = { Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "continue")},
                                onClick = { viewModel.onEvent(TrainingScreenEvent.OnContinueClick) },
                                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                            )
                        } else {
                            ExtendedFloatingActionButton(
                                text = {
                                    Text(
                                        text = "Check",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                },
                                icon = { Icon(Icons.Filled.Check, "check an answer") },
                                onClick = { viewModel.onEvent(TrainingScreenEvent.OnCheckClick) },
                                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                            )
                        }

                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(18.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(5f)
                        .paint(
                            painterResource(id = viewModel.catDrawable),
                            contentScale = ContentScale.Fit
                        )
                        .padding(0.dp, 55.dp, 20.dp, 0.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = viewModel.esWord,
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp,
                        lineHeight = 40.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = Modifier.height(0.dp))
                Divider()
                Spacer(modifier = Modifier.height(14.dp))
                BasicTextField(
                    value = viewModel.uaWord,
                    textStyle = TextStyle(
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    onValueChange = { viewModel.onEvent(TrainingScreenEvent.OnUaWordChange(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(5f),
                    singleLine = false,
                    maxLines = 8
                )
            }
        } 
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Image(painter = painterResource(id = R.drawable.congratulating_cat), contentDescription = "congratulating cat")
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "You did it!",
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold

            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "XP gained: ${viewModel.gainedXp}",
                fontSize = 28.sp,
                fontWeight = FontWeight.Light

            )
            Spacer(modifier = Modifier.height(54.dp))
            Button(
                onClick = { viewModel.onEvent(TrainingScreenEvent.OnTrainingCompleted) }
            ) {
                Text(
                    text = "Great!",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}