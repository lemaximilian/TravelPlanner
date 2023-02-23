package com.example.travelplanner.view

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.travelplanner.model.Trip
import com.example.travelplanner.viewmodel.MainViewModel

@Composable
fun TripView(navController: NavController, viewModel: MainViewModel, trip: Trip?) {
    Scaffold(
        topBar = { TopAppBar() {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        }
        },
        content = { padding ->

        },
        floatingActionButton = { FloatingActionButton(onClick = {}) {
            Text("X")
        }
        },
        bottomBar = { BottomAppBar() {
            Text(trip!!.name)
        }
        }
    )
}

