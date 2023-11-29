package com.example.lingualearn.days_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lingualearn.R
import com.example.lingualearn.utils.UiEventForUser
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaysScreen(
    onNavigate: (UiEventForUser.Navigate) -> Unit,
    viewModel: DaysScreenViewModel = hiltViewModel()
) {
    val daysList = viewModel.daysList.collectAsState(initial = emptyList())
    val daysCount = viewModel.totalWordsCount.collectAsState(initial = 0)
    val xpCount = viewModel.totalXpCount.collectAsState(initial = 0)
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()


    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEventForUser.Navigate -> {
                    onNavigate(event)
                }

                else -> Unit
            }
        }
    }


    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                modifier = Modifier.fillMaxWidth(0.09f),
                                painter = painterResource(id = R.drawable.calendar),
                                contentDescription = ""
                            )
                            Text(
                                text = "Day ${viewModel.currentDayId}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                textAlign = TextAlign.Center
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                modifier = Modifier.fillMaxWidth(0.1f),
                                painter = painterResource(id = R.drawable.dictionary),
                                contentDescription = ""
                            )
                            Text(
                                text = "Words total: ${daysCount.value}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                textAlign = TextAlign.Center
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                modifier = Modifier.fillMaxWidth(0.14f),
                                painter = painterResource(id = R.drawable.medal),
                                contentDescription = ""
                            )
                            Text(
                                text = "XP: ${xpCount.value}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                })
        },
        bottomBar = {
            if (viewModel.lastDate != LocalDate.now()) {
                BottomAppBar(
                    modifier = Modifier.clip(RoundedCornerShape(28.dp, 28.dp, 0.dp, 0.dp)),
                    actions = {
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Start a new day!",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            textAlign = TextAlign.Center
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { viewModel.onEvent(DaysScreenEvent.OnStartNewDayClick) },
                            containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                        ) {
                            Icon(Icons.Filled.Add, "")
                        }
                    }

                )
            } else {
                BottomAppBar(
                    modifier = Modifier.clip(RoundedCornerShape(28.dp, 28.dp, 0.dp, 0.dp)),
                    actions = {

                    },
                    floatingActionButton = {
                        ExtendedFloatingActionButton(
                            text = { Text(text = "Practice words") },
                            icon = {
                                Icon(
                                    modifier = Modifier.fillMaxHeight(0.5f),
                                    painter = painterResource(id = R.drawable.kitty1),
                                    contentDescription = ""
                                )
                            },
                            onClick = { viewModel.onEvent(DaysScreenEvent.OnTrainingClick) },
                            containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                        )
                    }

                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(6.dp, 0.dp, 6.dp, 6.dp)
                    .clip(RoundedCornerShape(0.dp, 0.dp, 36.dp, 36.dp))
            ) {
                items(daysList.value) { day ->
                    DayItem(day = day, onEvent = viewModel::onEvent)
                }
            }
        }
    }
}