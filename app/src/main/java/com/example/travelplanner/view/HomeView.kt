package com.example.travelplanner.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
        if(tripListViewModel.tripList().isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Keine Reise verfügbar")
            }
        }
        else
            TripGrid(tripListViewModel, padding)
    }
}

@Composable
fun TripGrid(tripListViewModel: TripListViewModel, padding: PaddingValues) {
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(padding)
    ) {
        header {
            Text("Ihre Reisen", fontWeight = FontWeight.Bold, modifier = Modifier.padding(PaddingValues(horizontal = 8.dp)))
        }
        items(tripListViewModel.tripList()) { trip ->
            TripCard(trip)
        }
    }
}

@Composable
fun TripCard(trip: Trip){
    Button(onClick = { /*TODO*/ },
        modifier = Modifier
            .height(180.dp)
            .width(180.dp)
            .padding(paddingValues = PaddingValues(horizontal = 4.dp))
    ) {
        Text(trip.name)
    }
}

fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}