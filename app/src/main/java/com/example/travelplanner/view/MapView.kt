package com.example.travelplanner.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.travelplanner.viewmodel.MainViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapView(navController: NavHostController, viewModel: MainViewModel) {
    Scaffold(
        topBar = { TopAppBar() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
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
            ) {

            }
        },
        bottomBar = { BottomAppBar() {
            BottomNavigation(navController = navController)
        }
        }
    )
}