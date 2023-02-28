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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.travelplanner.model.Expenses
import com.example.travelplanner.model.Trip
import com.example.travelplanner.viewmodel.ExpensesViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.*

@Composable
fun ExpensesView(navController: NavController, expensesViewModel: ExpensesViewModel, tripJson: String) {
    val context = LocalContext.current
    val trip = Json.decodeFromString<Trip>(tripJson)
    val expensesList = expensesViewModel.readAllData.observeAsState(listOf()).value

    Scaffold(
        topBar = { TopAppBar {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        }
        },
        bottomBar = {
            BottomAppBar() {
                Text("Fügen Sie hier Kosten für Ihre Reise hinzu")
            }
        },
        floatingActionButton = {
            val openDialog = remember { mutableStateOf(false) }
            val textStateDescription = remember { mutableStateOf(TextFieldValue()) }
            val textStateAmount = remember { mutableStateOf(TextFieldValue()) }

            FloatingActionButton(onClick = { openDialog.value = true }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }

            if(openDialog.value) {
                AlertDialog(
                    onDismissRequest = { openDialog.value = false },
                    title = {
                        Text(
                            "Kosten hinzufügen",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    text = {
                        Column {
                            TextField(
                                value = textStateDescription.value,
                                label = { Text("Bezeichnung${System.lineSeparator()}(z.B. Mietwagen, Tanken, Hotel)") },
                                onValueChange = {
                                    textStateDescription.value = it
                                })
                            Spacer(modifier = Modifier.padding(vertical = 5.dp))
                            TextField(
                                value = textStateAmount.value,
                                label = { Text("Betrag (in €)") },
                                onValueChange = {
                                    textStateAmount.value = it
                                })
                        }
                    },
                    confirmButton = {
                        Button(onClick = {
                            if(textStateDescription.value.text.isNotEmpty() && textStateAmount.value.text.isNotEmpty()) {
                                expensesViewModel.addExpenses(Expenses(UUID.randomUUID(), trip.id, textStateDescription.value.text, textStateAmount.value.text.toDouble()))
                                openDialog.value = false
                                textStateDescription.value = TextFieldValue("")
                                textStateAmount.value = TextFieldValue("")
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
        if(expensesList.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Keine Kosten hinzugefügt")
            }
        } else {
            var totalAmount = 0.0
            expensesList.forEach { totalAmount += it.amount }

            Column(
                modifier = Modifier.fillMaxHeight(),
            ) {
                ExpensesList(expensesViewModel, trip, expensesList)
                Text(
                    "Gesamtkosten: $totalAmount€",
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun ExpensesList(expensesViewModel: ExpensesViewModel, trip: Trip, expensesList: List<Expenses>) {
    LazyColumn {
        stickyHeader {
            Text(
                "Kosten für Ihre Reise",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        items(expensesList) { expenses ->
            ListItem(
                text = { Text("${expenses.description} (${expenses.amount}€)") },
                icon = {
                    IconButton(onClick = {
                        expensesViewModel.deleteExpenses(expenses)
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