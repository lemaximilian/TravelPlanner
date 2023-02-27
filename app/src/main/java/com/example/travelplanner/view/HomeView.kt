package com.example.travelplanner.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.travelplanner.model.Trip
import com.example.travelplanner.viewmodel.MainViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

@Composable
fun HomeView(navController: NavHostController, viewModel: MainViewModel, userName: String, tripList: List<Trip>) {
    Scaffold(
        topBar = {
            TopAppBar() {
                Text("Travelplanner")
            }
        },
        bottomBar = {
            BottomNavigation(navController = navController)
        },
        floatingActionButton = {
            val openDialog = remember { mutableStateOf(false) }
            val textState = remember { mutableStateOf(TextFieldValue()) }

            FloatingActionButton(onClick = { openDialog.value = true }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }

            if(openDialog.value) {
                AlertDialog(
                    onDismissRequest = { openDialog.value = false },
                    title = {
                        Text(
                            "Reise erstellen",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    text = {
                           TextField(value = textState.value, onValueChange = {
                               textState.value = it
                           })
                    },
                    confirmButton = {
                        Button(onClick = {
                            viewModel.addTrip(Trip(UUID.randomUUID(), textState.value.text))
                            openDialog.value = false
                        }) {
                            Text("Erstellen")
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
    ) { padding ->
        Column {
            if(tripList.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Keine Reise verfügbar")
                }
            }
            else
                TripGrid(navController, viewModel, userName, tripList, padding)
        }
    }
}

@Composable
fun TripGrid(navController: NavHostController, viewModel: MainViewModel, userName: String, tripList: List<Trip>, padding: PaddingValues) {
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(padding)
    ) {
        header {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Wilkommen, $userName", fontWeight = FontWeight.Bold)
                Text(
                    "Ihre Reisen",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(PaddingValues(horizontal = 8.dp))
                )
            }
        }
        items(tripList) { trip ->
            TripCard(navController, trip)
        }
    }
}

@Composable
fun TripCard(navController: NavHostController, trip: Trip) {
    val tripJson = Json.encodeToString(trip)
    Button(onClick = { navController.navigate("TripView/$tripJson") },
        modifier = Modifier
            .height(180.dp)
            .width(180.dp)
            .padding(paddingValues = PaddingValues(horizontal = 4.dp))
    ) {
        Text(trip.name)
    }
}

fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}


