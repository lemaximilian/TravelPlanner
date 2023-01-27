package com.example.travelplanner.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.travelplanner.model.BottomNavItem
import com.example.travelplanner.model.Trip
import com.example.travelplanner.viewmodel.TripListViewModel

@Composable
fun NavHostView(navController: NavHostController, tripListViewModel: TripListViewModel, tripList: List<Trip>) {
    NavHost(navController = navController, startDestination = "home") {
        composable(BottomNavItem.Main.screen_route) { HomeView(navController, tripListViewModel, tripList) }
        composable(BottomNavItem.AddTrip.screen_route) { AddTripView(navController, tripListViewModel) }
        composable(BottomNavItem.Settings.screen_route) { SettingsView(navController) }

    }
}