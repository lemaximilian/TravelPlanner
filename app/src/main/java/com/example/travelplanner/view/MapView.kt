package com.example.travelplanner.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.travelplanner.viewmodel.MainViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapView(navController: NavController, viewModel: MainViewModel) {
    Scaffold(
        topBar = { TopAppBar() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier)
                Text("MapsDemo")
                Spacer(modifier = Modifier)
            }
        }
        },
        content = { padding ->
            val thkoeln = LatLng(51.02, 7.56)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(thkoeln, 15f)
            }
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            )
        },
        bottomBar = { BottomAppBar() {
            Text("GoogleMaps")
        }
        }
    )
}