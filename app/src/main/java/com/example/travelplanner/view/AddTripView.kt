package com.example.travelplanner.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun AddTripView(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar() {
                Text("AddTripView")
            }
        },
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) { padding ->
        Text(text = "AddTripView", modifier = Modifier.padding(padding))
    }
}