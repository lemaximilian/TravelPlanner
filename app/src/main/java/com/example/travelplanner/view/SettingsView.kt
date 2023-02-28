package com.example.travelplanner.view



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
       ToDoList(todoViewModel = viewModel, context = LocalContext.current)
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
