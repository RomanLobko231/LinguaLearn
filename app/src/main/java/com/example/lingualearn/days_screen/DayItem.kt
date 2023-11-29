package com.example.lingualearn.days_screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lingualearn.data.Day
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayItem(
    day: Day,
    onEvent: (DaysScreenEvent) -> Unit
) {
    var itemHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    ElevatedCard(
        colors = if (day.date == LocalDate.now().toString()) {
            CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
        } else {
            CardDefaults.elevatedCardColors()
        },
        onClick = { onEvent(DaysScreenEvent.OnDayClick(day)) },
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.elevatedCardElevation(5.dp),
        modifier = if (day.date == LocalDate.now().toString()) {
            Modifier
                .onSizeChanged {
                    with(density) { itemHeight = it.height.toDp() }
                }
                .aspectRatio(3f)

                .padding(8.dp)
                .shadow(
                    shape = RoundedCornerShape(30.dp),
                    ambientColor = MaterialTheme.colorScheme.primary,
                    elevation = 2.dp,
                    clip = true
                )
        } else {
            Modifier
                .onSizeChanged {
                    with(density) { itemHeight = it.height.toDp() }
                }
                .aspectRatio(4f)
                .padding(8.dp)
                .shadow(
                    shape = RoundedCornerShape(30.dp),
                    ambientColor = MaterialTheme.colorScheme.primary,
                    elevation = 2.dp,
                    clip = true
                )
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp)
        ) {
            if (day.date == LocalDate.now().toString()){
                Text(
                    text = "Day " + day.id.toString(),
                    modifier = Modifier.weight(5f),
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            } else{
                Text(
                    text = "Day " + day.id.toString(),
                    modifier = Modifier.weight(5f),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            Spacer(modifier = Modifier.weight(3f))
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "to words",
                modifier = Modifier.weight(1f)
            )
        }
    }
}