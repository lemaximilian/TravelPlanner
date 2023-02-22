package com.example.travelplanner.view


import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController



@Composable
fun SettingsView(navController: NavHostController) {
   Scaffold(
        topBar = {
            TopAppBar() {
                Text("Einstellungen",Modifier.padding(horizontal = 150.dp))
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


    val toDoList = remember { mutableStateListOf<String>()}
    val (text, setText) = remember { mutableStateOf("") }
    val checkedState = remember { mutableStateListOf<Boolean>() }



    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = setText,
                placeholder = { Text("Neues Element") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    toDoList.add(text)
                    checkedState.add(false)
                    setText("")
                }
            ) {
                Text(text = "Hinzufügen")
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(toDoList) { index, item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)

                ) {
                    Checkbox(
                        checked = checkedState[index],
                        onCheckedChange = { checked ->
                            checkedState[index] = checked

                        },
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = item,
                        fontSize = 18.sp,
                        textDecoration = if (checkedState[index]) TextDecoration.LineThrough else TextDecoration.None,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {
                        toDoList.remove(item)
                        checkedState.removeAt(index)

                    }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Löschen")
                    }
                }
            }
        }
    }
}