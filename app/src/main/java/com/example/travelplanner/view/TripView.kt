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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.travelplanner.model.Trip
import com.example.travelplanner.viewmodel.MainViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
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
            TripStartDestinationSection(viewModel, trip)
        }
        item {
            TripTravelerSection(navController, viewModel, trip)
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
fun TripStartDestinationContent(viewModel: MainViewModel, trip: Trip) {
    val tripLive = viewModel.getTripByID(trip.id).observeAsState().value

    Column {
        TripStartDestinationButton(viewModel, trip)
        if(tripLive?.start == null && tripLive?.destination == null) {
            Text(
                    "Es wurde bisher kein Start und Ziel hinzugefügt.",
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
        } else {
            Column {
                Text(
                    "Start: " + tripLive?.start,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    "Ziel: " + tripLive?.destination,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

    }
}

@Composable
fun TripStartDestinationButton(viewModel: MainViewModel, trip: Trip) {
    val openDialog = remember { mutableStateOf(false) }
    val textStateStart = remember { mutableStateOf(TextFieldValue()) }
    val textStateDestination = remember { mutableStateOf(TextFieldValue()) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Start und Ziel der Reise",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.padding(8.dp),
            color = Color.White
        )
        IconButton(onClick = { openDialog.value = true }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add", tint = Color.White)
        }
        if(openDialog.value) {
            AlertDialog(
                onDismissRequest = { openDialog.value = false },
                title = {
                    Text(
                        "Start und Ziel der Reise hinzufügen",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                },
                text = {
                    Column {
                        TextField(
                            value = textStateStart.value,
                            label = { Text("Start der Reise") },
                            onValueChange = {
                                textStateStart.value = it
                            })
                        Spacer(modifier = Modifier.padding(vertical = 5.dp))
                        TextField(
                            value = textStateDestination.value,
                            label = { Text("Ziel der Reise") },
                            onValueChange = {
                                textStateDestination.value = it
                            })
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        viewModel.updateTrip(Trip(trip.id, trip.name, textStateStart.value.text, textStateDestination.value.text))
                        openDialog.value = false
                        textStateStart.value = TextFieldValue("")
                        textStateDestination.value = TextFieldValue("")
                    }) {
                        Text("Hinzufügen")
                    }
                },
                dismissButton = {
                    Button(onClick = { openDialog.value = false }) {
                        Text("Abbrechen")
                    }
                }
            )
        }
    }
}

@Composable
fun TripTravelerContent(navController: NavController, viewModel: MainViewModel, trip: Trip) {
    val travelerList = viewModel.readAllDataTraveler.observeAsState(listOf()).value
    val totalTraveler = travelerList.size

    Column {
        TripTravelerButton(navController, viewModel, trip)
        Text(
            "Zurzeit gibt es $totalTraveler Reisende(n)",
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun TripTravelerButton(navController: NavController, viewModel: MainViewModel, trip: Trip) {
    val tripJson = Json.encodeToString(trip)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Reisende(r)",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.padding(8.dp),
            color = Color.White
        )
        IconButton(onClick = {
            navController.navigate("TravelerView/$tripJson")
        }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add", tint = Color.White)
        }
    }
}

@Composable
fun TripStartDestinationSection(viewModel: MainViewModel, trip: Trip) {
    Box {
        TripSection()
        TripStartDestinationContent(viewModel, trip)
    }
}

@Composable
fun TripTravelerSection(navController: NavController, viewModel: MainViewModel, trip: Trip) {
    Box {
        TripSection()
        TripTravelerContent(navController, viewModel, trip)
    }
}

