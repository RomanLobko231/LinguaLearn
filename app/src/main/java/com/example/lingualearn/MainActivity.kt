package com.example.lingualearn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lingualearn.days_screen.DaysScreen
import com.example.lingualearn.training_screen.TrainingScreen
import com.example.lingualearn.ui.theme.LinguaLearnTheme
import com.example.lingualearn.utils.Screen
import com.example.lingualearn.words_screen.WordsScreen
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LinguaLearnTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.DaysScreen.route
                ) {
                    composable(Screen.DaysScreen.route) {
                        DaysScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                    composable(
                        route = Screen.WordsScreen.route + "?dayId={dayId}",
                        arguments = listOf(
                            navArgument(name = "dayId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        WordsScreen()
                    }
                    composable(
                        route = Screen.TrainingScreen.route + "?dayId={dayId}",
                        arguments = listOf(
                            navArgument(name = "dayId"){
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ){
                        TrainingScreen(
                            onNavigate = {
                            navController.navigate(it.route)
                        })
                    }
                }
            }
        }

    }

}
