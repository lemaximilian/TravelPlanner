package com.example.travelplanner.view

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.travelplanner.model.Trip
import com.example.travelplanner.viewmodel.ExpensesViewModel
import com.example.travelplanner.viewmodel.MainViewModel
import com.example.travelplanner.viewmodel.TravelerViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun TripView(navController: NavController, viewModel: MainViewModel, travelerViewModel: TravelerViewModel, expensesViewModel: ExpensesViewModel, tripJson: String) {
    val trip = Json.decodeFromString<Trip>(tripJson)
    Scaffold(
        topBar = { TopAppBar() {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        }
        },
        content = { padding ->
            TripContent(navController, viewModel, travelerViewModel, expensesViewModel, trip)
        },
        bottomBar = { BottomAppBar() {
            Text(trip.name)
        }
        }
    )
}

@Composable
fun TripContent(navController: NavController, viewModel: MainViewModel, travelerViewModel: TravelerViewModel, expensesViewModel: ExpensesViewModel, trip: Trip) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HeaderAndDelete(navController, viewModel, trip)
        }
        item {
            StartDestinationSection(viewModel, trip)
        }
        item {
            TravelerSection(navController, travelerViewModel, trip)
        }
        item {
            ExpensesSection(navController, expensesViewModel, trip)
        }
        item {
            ToDoSection(navController, trip)
        }
        item {
            PlaceholderSection()
        }
    }
}

@Composable
fun HeaderAndDelete(navController: NavController, viewModel: MainViewModel, trip: Trip) {
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
fun Section() {
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
fun StartDestinationContent(viewModel: MainViewModel, trip: Trip) {
    val tripLive = viewModel.getTripByID(trip.id).observeAsState().value

    Column {
        StartDestinationButton(viewModel, trip)
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
fun StartDestinationButton(viewModel: MainViewModel, trip: Trip) {
    val context = LocalContext.current
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
                        if(textStateStart.value.text.isNotEmpty() && textStateDestination.value.text.isNotEmpty()) {
                            viewModel.updateTrip(Trip(trip.id, trip.name, textStateStart.value.text, textStateDestination.value.text))
                            openDialog.value = false
                            textStateStart.value = TextFieldValue("")
                            textStateDestination.value = TextFieldValue("")
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
}

@Composable
fun TravelerContent(navController: NavController, travelerViewModel: TravelerViewModel, trip: Trip) {
    val travelerList = travelerViewModel.readAllData.observeAsState(listOf()).value
    val totalTraveler = travelerList.size

    Column {
        TravelerButton(navController, trip)
        Text(
            "Zurzeit gibt es $totalTraveler Reisende(n)",
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun TravelerButton(navController: NavController, trip: Trip) {
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
fun ExpensesContent(navController: NavController, expensesViewModel: ExpensesViewModel, trip: Trip) {
    val expensesList = expensesViewModel.readAllData.observeAsState(listOf()).value

    Column {
        ExpensesButton(navController, trip)
        if(expensesList.isNotEmpty()) {
            var totalAmount = 0.0
            expensesList.forEach { totalAmount += it.amount }
            Text(
                "Derzeitige Gesamtkosten der Reise: $totalAmount€",
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        } else {
            Text(
                "Derzeitige Gesamtkosten der Reise: 0.00€",
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ExpensesButton(navController: NavController, trip: Trip) {
    val tripJson = Json.encodeToString(trip)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Reisekosten",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.padding(8.dp),
            color = Color.White
        )
        IconButton(onClick = {
            navController.navigate("ExpensesView/$tripJson")
        }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add", tint = Color.White)
        }
    }
}

@Composable
fun ToDoContent(navController: NavController, trip: Trip) {
    Column {
        ToDoButton(navController, trip)
    }
}

@Composable
fun ToDoButton(navController: NavController, trip: Trip) {
    val tripJson = Json.encodeToString(trip)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("To-Do Liste",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.padding(8.dp),
            color = Color.White
        )
        IconButton(onClick = {
            navController.navigate("ToDoView/$tripJson")
        }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add", tint = Color.White)
        }
    }
}

@Composable
fun StartDestinationSection(viewModel: MainViewModel, trip: Trip) {
    Box {
        Section()
        StartDestinationContent(viewModel, trip)
    }
}

@Composable
fun TravelerSection(navController: NavController, travelerViewModel: TravelerViewModel, trip: Trip) {
    Box {
        Section()
        TravelerContent(navController, travelerViewModel, trip)
    }
}

@Composable
fun ExpensesSection(navController: NavController, expensesViewModel: ExpensesViewModel, trip: Trip) {
    Box {
        Section()
        ExpensesContent(navController, expensesViewModel, trip)
    }
}

@Composable
fun ToDoSection(navController: NavController, trip: Trip) {
    Box {
        Section()
        ToDoContent(navController, trip)
    }
}

@Composable
fun PlaceholderSection() {
    Box {
        Section()
    }
}

