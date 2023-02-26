package com.example.travelplanner.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.travelplanner.model.Trip
import com.example.travelplanner.viewmodel.MainViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Composable
fun TripView(navController: NavController, viewModel: MainViewModel, tripJson: String) {
    val trip = Json.decodeFromString<Trip>(tripJson)
    Scaffold(
        topBar = { TopAppBar() {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        }
        },
        content = { padding ->
            TripContent(navController, viewModel, trip)
        },
        floatingActionButton = { FloatingActionButton(onClick = {}) {
            Text("X")
        }
        },
        bottomBar = { BottomAppBar() {
            Text(trip.name)
        }
        }
    )
}

@Composable
fun TripContent(navController: NavController, viewModel: MainViewModel, trip: Trip) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        item {
            TripHeaderAndDelete(navController, viewModel, trip)
        }
    }
}

@Composable
fun TripHeaderAndDelete(navController: NavController, viewModel: MainViewModel, trip: Trip) {
    Row {
        Text(text = trip.name, fontWeight = FontWeight.Bold, fontSize = 50.sp)
        Spacer(modifier = Modifier.padding(horizontal = 120.dp))
        IconButton(onClick = {
            navController.navigateUp()
            viewModel.deleteTrip(trip)
        }) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red)
        }
    }
}

