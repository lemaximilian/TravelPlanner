package com.example.travelplanner.view

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.travelplanner.model.Trip
import com.example.travelplanner.viewmodel.MainViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

@Composable
fun HomeView(navController: NavHostController, viewModel: MainViewModel) {
    val context = LocalContext.current
    val tripList = viewModel.readAllData.observeAsState(listOf()).value

    Scaffold(
        topBar = {
            TopAppBar() {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier)
                    Text("TravelPlanner")
                    Spacer(modifier = Modifier)
                }
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
                           TextField(
                               value = textState.value,
                               label = { Text("Name der Reise") },
                               onValueChange = {
                               textState.value = it
                           })
                    },
                    confirmButton = {
                        Button(onClick = {
                            if(textState.value.text.isNotEmpty()) {
                                viewModel.addTrip(Trip(UUID.randomUUID(), textState.value.text, null, null))
                                openDialog.value = false
                                textState.value = TextFieldValue("")
                            } else {
                                Toast.makeText(context,"Ungültige Eingabe!",Toast.LENGTH_SHORT).show()
                            }
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
            Text(
                "Willkommen, ${viewModel.getUserName.collectAsState("").value}",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier.padding(PaddingValues(horizontal = 8.dp))
            )
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
                TripGrid(navController, tripList, padding)
        }
    }
}

@Composable
fun TripGrid(navController: NavHostController, tripList: List<Trip>, padding: PaddingValues) {
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
        Text(
            trip.name,
            fontSize = 20.sp
        )
    }
}

fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}


