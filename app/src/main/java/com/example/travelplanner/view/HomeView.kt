package com.example.travelplanner.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun HomeView(navController: NavHostController) {
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
        LazyColumn(modifier = Modifier.padding(padding)) {
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