package com.example.travelplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.travelplanner.ui.theme.TravelPlannerTheme
import com.example.travelplanner.view.NavHostView
import com.example.travelplanner.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel by viewModels<MainViewModel>()
        setContent {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            TravelPlannerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val tripList by viewModel.getTripList().observeAsState(emptyList())
                    NavHostView(navController = navController, viewModel = viewModel, tripList = tripList)
                }
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TravelPlannerTheme {
        val viewModel = MainViewModel()
        val navController = rememberNavController()
        val tripList by viewModel.getTripList().observeAsState(emptyList())
        NavHostView(navController = navController, viewModel = viewModel, tripList = tripList)
    }
}