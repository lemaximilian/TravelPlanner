package com.example.travelplanner.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.travelplanner.model.Trip
import com.example.travelplanner.viewmodel.TripListViewModel

@Composable
fun HomeView(navController: NavHostController, tripListViewModel: TripListViewModel) {
    Scaffold(
        topBar = {
            TopAppBar() {
                Text("TravelPlanner")
            }
        },
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) { padding ->
        if(tripListViewModel.tripList().isEmpty())
            Text("Keine Reisen verfügbar")
        else
            TripGrid(tripListViewModel, padding)
    }
}

@Composable
fun TripGrid(tripListViewModel: TripListViewModel, padding: PaddingValues) {
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(padding)
    ) {
        items(tripListViewModel.tripList()) { trip ->
            TripCard(trip)
        }
    }
}

@Composable
fun TripCard(trip: Trip){
    Button(onClick = { /*TODO*/ },
        modifier = Modifier
            .height(175.dp)
            .width(175.dp)
            .padding(paddingValues = PaddingValues(horizontal = 8.dp))
    ) {
        Text(trip.name)
    }
}

@Composable
fun Box(shape: RoundedCornerShape){
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.Center)) {
        Box(
            modifier = Modifier
                .size(185.dp)
                .clip(shape)
                .background(Color.Red)
        )
    }
}