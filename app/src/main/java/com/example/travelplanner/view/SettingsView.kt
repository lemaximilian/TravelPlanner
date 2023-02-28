package com.example.travelplanner.view



import android.app.Application
import android.content.Context

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import android.widget.Toast
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import com.example.travelplanner.model.Todo
import com.example.travelplanner.viewmodel.TodoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun SettingsView(navController: NavHostController,viewModel: TodoViewModel) {
    val context = LocalContext.current
   Scaffold(
        topBar = {
            TopAppBar() {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier)
                    Text("Einstellungen")
                    Spacer(modifier = Modifier)
                }
            }
        },
        bottomBar = {
       BottomNavigation(navController = navController)
        }
    ) { padding ->
       Text(text = "SettingsView", modifier = Modifier.padding(padding))
       ToDoList(todoViewModel = TodoViewModel(application = Application()), context = context )
   }

}







