package com.example.travelplanner.view

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.travelplanner.model.Todo
import com.example.travelplanner.model.Trip
import com.example.travelplanner.viewmodel.TodoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Composable
fun ToDoView(navController: NavController, tripJson: String, viewModel: TodoViewModel) {
    val context = LocalContext.current
    val trip = Json.decodeFromString<Trip>(tripJson)

    Scaffold(
        topBar = { TopAppBar {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        }
        },
        bottomBar = {
            BottomAppBar() {
                Text("To-Do Liste")
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        Text(text = "ToDo", modifier = Modifier.padding(padding))
        ToDoList(todoViewModel = TodoViewModel(Application()), context = context )

    }
}








@Composable
fun ToDoList(todoViewModel: TodoViewModel, context: Context) {
    val (text, setText) = remember { mutableStateOf("") }
    val todoItems by todoViewModel.todoItems.observeAsState(emptyList())
    val context = LocalContext.current

    // Elemente hinzufügen
    fun addItem(item: String) {
        if (item.isNotEmpty()) {
            todoViewModel.viewModelScope.launch(Dispatchers.IO) {
                todoViewModel.addTodoItem(item)
                withContext(Dispatchers.Main) {

                    Toast.makeText(context, "\"$item\" erfolgreich hinzugefügt", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } else {
            Toast.makeText(context, "Ungültige eingabe!", Toast.LENGTH_SHORT).show()
        }
    }


    fun removeItem(item: Todo) {
        todoViewModel.viewModelScope.launch(Dispatchers.IO) {
            todoViewModel.deleteTodoItem(item)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { addItem(text); setText("") },
                modifier = Modifier
                    .padding(bottom = 56.dp),
                content = {
                    Icon(Icons.Filled.Add, contentDescription = "Hinzufügen")
                }
            )
        }
    ) {
        Column {
            TextField(
                value = text,
                onValueChange = setText,
                label = { Text("Aktivität hier hinzufügen") },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            LazyColumn {
                items(todoItems.size) { index ->
                    val item = todoItems[index]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = item.checked,
                            onCheckedChange = {
                                val updatedItem = item.copy(checked = it)
                                todoViewModel.updateTodoItem(updatedItem)
                            }
                        )
                        Text(
                            item.title,
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .weight(1f)
                        )
                        IconButton(onClick = { removeItem(item) }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Löschen")
                        }
                    }
                }
            }
        }
    }
    LaunchedEffect(todoItems) {}
}