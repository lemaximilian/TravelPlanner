package com.example.travelplanner.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.travelplanner.viewmodel.MainViewModel

@Composable
fun AddTripView(navController: NavHostController, viewModel: MainViewModel) {
    val textState = remember { mutableStateOf(TextFieldValue()) }

    Scaffold(
        topBar = {
            TopAppBar() {
                Text("AddTripView")
            }
        },
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) { _ ->
        Column(Modifier.padding(16.dp)) {
            Text("Name der Reise", fontWeight = FontWeight.Bold)
            TextField(value = textState.value, onValueChange = {
                textState.value = it
            })
            Text("Die Reise hat folgenden Namen: " + textState.value.text)
            Button(onClick = {
                viewModel.addTrip(textState.value.text)
            }) {
                Text("Reise hinzufügen")
            }
        }
    }
}