package com.example.travelplanner.view

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.travelplanner.model.Traveler
import com.example.travelplanner.model.Trip
import com.example.travelplanner.viewmodel.MainViewModel
import com.example.travelplanner.viewmodel.TravelerViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.*

@Composable
fun TravelerView(navController: NavController, travelerViewModel: TravelerViewModel, tripJson: String) {
    val context = LocalContext.current
    val trip = Json.decodeFromString<Trip>(tripJson)
    val travelerList = travelerViewModel.readAllData.observeAsState(listOf()).value

    Scaffold(
        topBar = { TopAppBar {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        }
        },
        bottomBar = {
            BottomAppBar() {
                Text("Fügen Sie hier Reisende hinzu")
            }
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
                            "Reisenden hinzufügen",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    text = {
                        TextField(
                            value = textState.value,
                            label = { Text("Name des Reisenden") },
                            onValueChange = {
                                textState.value = it
                            })
                    },
                    confirmButton = {
                        Button(onClick = {
                            if(textState.value.text.isNotEmpty()) {
                                travelerViewModel.addTraveler(Traveler(UUID.randomUUID(), trip.id, textState.value.text))
                                openDialog.value = false
                                textState.value = TextFieldValue("")
                            } else {
                                Toast.makeText(context,"Ungültige Eingabe!", Toast.LENGTH_SHORT).show()
                            }

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
    ) { padding ->
        if(travelerList.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Keine Reisenden hinzugefügt")
            }
        } else
            TravelerList(travelerViewModel, trip, travelerList)
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun TravelerList(travelerViewModel: TravelerViewModel, trip: Trip, travelerList: List<Traveler>) {
    LazyColumn {
        stickyHeader {
            Text(
                "Reisende(r)",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        items(travelerList) { traveler ->
            ListItem(
                text = { Text(traveler.name) },
                icon = {
                    IconButton(onClick = {
                        travelerViewModel.deleteTraveler(traveler)
                    }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
            )
            Divider()
        }
    }
}
