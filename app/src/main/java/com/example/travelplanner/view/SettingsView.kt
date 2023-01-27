package com.example.travelplanner.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SettingsView(navController: NavHostController) {
   Scaffold(
        topBar = {
            TopAppBar() {
                Text("Einstellungen",Modifier.padding(horizontal = 150.dp))
            }
        },
        bottomBar = {
       BottomNavigation(navController = navController)
        }
    ) { padding ->
        Text(text = "SettingsView", modifier = Modifier.padding(padding))

    }



}