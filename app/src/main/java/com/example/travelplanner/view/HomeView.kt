package com.example.travelplanner.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.travelplanner.model.Trip
import com.example.travelplanner.viewmodel.TripListViewModel

@Composable
fun HomeView(navController: NavHostController, tripListViewModel: TripListViewModel, tripList: List<Trip>) {
    Scaffold(
        topBar = {
            TopAppBar() {
                Text("TravelPlanner")
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
                            tripListViewModel.addTrip(textState.value.text)
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
                TripGrid(navController, tripListViewModel, tripList, padding)
        }
    }
}

@Composable
fun TripGrid(navController: NavHostController, tripListViewModel: TripListViewModel, tripList: List<Trip>, padding: PaddingValues) {
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(padding)
    ) {
        header {
            Text("Ihre Reisen", fontWeight = FontWeight.Bold, modifier = Modifier.padding(PaddingValues(horizontal = 8.dp)))
        }
        items(tripList) { trip ->
            TripCard(navController, trip)
        }
    }
}

@Composable
fun TripCard(navController: NavHostController, trip: Trip){
    Button(onClick = { navController.navigate("TripView/" + trip.id) },
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

