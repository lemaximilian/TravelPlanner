package com.example.travelplanner.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
fun SettingsView(navController: NavHostController) {
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
       ToDoList()
   }

}




@Composable
fun ToDoList() {
    val db = FirebaseDatabase.getInstance()
    val ref = remember { db.reference.child("ToDo").child("Items") }
    val toDoList = remember { mutableStateListOf<String>() }
    val checkedState = remember { mutableStateListOf<Boolean>() }
    val (isLoaded, setIsLoaded) = remember { mutableStateOf(false) }
    val (text, setText) = remember { mutableStateOf("") }
    val context = LocalContext.current

    if (!isLoaded) {
        SideEffect {
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { item ->
                        val title = item.child("title").getValue(String::class.java)
                        val checked = item.child("checked").getValue(Boolean::class.java)
                        if (title != null && checked != null) {
                            toDoList.add(title)
                            checkedState.add(checked)
                        }
                    }
                    setIsLoaded(true)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context,"Unerwarteter Fehler",Toast.LENGTH_SHORT)
                }
            })
        }
    }


    // Elemente hinzufügen
    fun addItem(item: String) {
        if (item.isNotEmpty()) {
            val key = ref.push().key!!
            val newItemRef = ref.child(key)
            newItemRef.child("title").setValue(item)
            newItemRef.child("checked").setValue(false)
            toDoList.add(item)
            checkedState.add(false)
            Toast.makeText(context,"\"$item\" Erfolgreich hinzugefügt",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context,"Ungültige Eingabe!",Toast.LENGTH_SHORT).show()


        }
    }

    fun removeItem(item: String) {
        ref.orderByChild("title").equalTo(item).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    childSnapshot.ref.removeValue()
                }
                val index = toDoList.indexOf(item)
                toDoList.removeAt(index)
                checkedState.removeAt(index)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"Unerwarteter Fehler",Toast.LENGTH_SHORT)
            }
        })
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
                itemsIndexed(toDoList) { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = checkedState[index],
                            onCheckedChange = {
                                checkedState[index] = it
                                ref.orderByChild("title").equalTo(item).addListenerForSingleValueEvent(object :
                                    ValueEventListener {

                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        for (childSnapshot in snapshot.children) {
                                            childSnapshot.child("checked").ref.setValue(it)
                                        }

                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        Toast.makeText(context,"Unerwarteter Fehler",Toast.LENGTH_SHORT)
                                    }
                                })

                            }

                        )
                        Text(
                            item,
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
}

@Composable
fun ToDoList(todoViewModel: TodoViewModel) {
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
