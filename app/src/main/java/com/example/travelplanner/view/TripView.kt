package com.example.travelplanner.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
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
        bottomBar = { BottomAppBar() {
            Text(trip.name)
        }
        }
    )
}

@Composable
fun TripContent(navController: NavController, viewModel: MainViewModel, trip: Trip) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TripHeaderAndDelete(navController, viewModel, trip)
        }
        item {
            TripStartDestinationSection()
        }
        item {
            TripTravelerSection()
        }
        item {
            TripTravelerSection()
        }
        item {
            TripTravelerSection()
        }
        item {
            TripTravelerSection()
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

@Composable
fun TripSection() {
    SectionBox(shape = RoundedCornerShape(10.dp))
}

@Composable
fun SectionBox(shape: Shape) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.Center)) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .clip(shape)
                .background(Color.Blue)

        )
    }
}

@Composable
fun TripStartDestinationContent() {
    Row {
        Text("Start und Ziel der Reise",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.padding(8.dp),
            color = Color.White
        )
        IconButton(onClick = {

        }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add", tint = Color.White)
        }
    }
}

@Composable
fun TripTravelerContent() {
    Text("Reisende(r)",
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(8.dp),
        color = Color.White
    )
}

@Composable
fun TripStartDestinationSection() {
    Box {
        TripSection()
        TripStartDestinationContent()
    }
}

@Composable
fun TripTravelerSection() {
    Box {
        TripSection()
        TripTravelerContent()
    }
}

