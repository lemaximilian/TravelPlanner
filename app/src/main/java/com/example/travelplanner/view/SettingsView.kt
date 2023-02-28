package com.example.travelplanner.view




import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
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
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.travelplanner.model.Todo
import com.example.travelplanner.viewmodel.MainViewModel
import com.example.travelplanner.viewmodel.TodoViewModel

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


@Composable
fun SettingsView(navController: NavHostController) {
    val todoViewModel: TodoViewModel = viewModel()
    val viewModelScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar() {
                Text("Einstellungen", Modifier.padding(horizontal = 150.dp))
            }
        },
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) { padding ->
        Text(text = "SettingsView", modifier = Modifier.padding(padding))
        ToDoList(todoViewModel)

    }



            /*var imageUris by remember { mutableStateOf(listOf<Uri>()) }

    Column {
        SelectImagesFromGallery { uris ->
            imageUris = uris
            uris.forEach { uri ->
                uploadImageToFirebaseStorage(uri)
            }
        }
       ImageList(images = imageUris)
    }*/
}



@Composable
fun ImageCard(painter: Painter, contenDescription: String, modifier: Modifier = Modifier,uri: Uri
){
    Card(modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp))
    {
     Box(modifier = Modifier.height(200.dp)){
         Image(
             painter = rememberAsyncImagePainter(model = uri),
             contentDescription = "Selected image",
             contentScale = ContentScale.Crop
         )


     }
    }

}





@Composable
fun ToDoListl() {
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
            Toast.makeText(context,"\"$item\" erfolgreich hinzugefügt",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context,"Ungültige eingabe!",Toast.LENGTH_SHORT).show()


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
fun SelectImagesFromGallery(onImagesSelected: (List<Uri>) -> Unit) {

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetMultipleContents()) { uris: List<Uri>? ->
        uris?.let { onImagesSelected(it) }
    }
    Button(
        onClick = {
            launcher.launch("image/*")
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Bilder aus Galerie auswählen")
    }
}



@Composable
fun ImageList(images: List<Uri>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(images.size) { index ->
            ImageListItem(imageUri = images[index])
        }
    }
}

@Composable
fun ImageListItem(imageUri: Uri) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = imageUri).apply(block = fun ImageRequest.Builder.() {
            crossfade(true)
        }).build()
    )
    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    )
}


val db = Firebase.storage
val storageRef = db.reference
val imagesRef = storageRef.child("images")

// This function uploads the selected image to Firebase Storage
fun uploadImageToFirebaseStorage(uri: Uri) {
    val filename = UUID.randomUUID().toString() + ".jpg"
    val ref = imagesRef.child(filename)
    val uploadTask = ref.putFile(uri)

    uploadTask.addOnSuccessListener {
        // Image upload successful
        Log.d(TAG, "Image uploaded successfully: ${it.metadata?.path}")
    }.addOnFailureListener {
        // Image upload failed
        Log.e(TAG, "Error uploading image", it)
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
















