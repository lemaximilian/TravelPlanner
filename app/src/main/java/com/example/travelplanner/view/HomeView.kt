package com.example.travelplanner.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.travelplanner.viewmodel.TripListViewModel

@Composable
fun HomeView(navController: NavHostController, tripListViewModel: TripListViewModel) {
    Scaffold(
        topBar = {
            TopAppBar() {
                Text("TravelPlanner")
            }
        },
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) { padding ->
        LazyColumn(Modifier.padding(padding)) {
            item {
                Button(onClick = { }) {
                    Text("Das ist der 1. Button")
                }
            }
            item {
                Button(onClick = { }) {
                    Text("Das ist der 2. Button")
                }
            }
            item {
                Button(onClick = { }) {
                    Text("Das ist der 3. Button")
                }
            }
        }
    }
}