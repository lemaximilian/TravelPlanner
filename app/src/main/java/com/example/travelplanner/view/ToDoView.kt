package com.example.travelplanner.view

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.travelplanner.model.Trip
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Composable
fun ToDoView(navController: NavController, tripJson: String) {
    val context = LocalContext.current
    val trip = Json.decodeFromString<Trip>(tripJson)

    Scaffold(
        topBar = { TopAppBar {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        }
        },
        bottomBar = {
            BottomAppBar() {
                Text("To-Do Liste")
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) { padding ->

    }
}