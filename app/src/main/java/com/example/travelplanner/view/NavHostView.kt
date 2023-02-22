package com.example.travelplanner.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.travelplanner.model.BottomNavItem
import com.example.travelplanner.model.Trip
import com.example.travelplanner.viewmodel.TripListViewModel
import java.util.*

@Composable
fun NavHostView(navController: NavHostController, tripListViewModel: TripListViewModel, tripList: List<Trip>) {
    NavHost(navController = navController, startDestination = "WelcomeView") {
        composable(BottomNavItem.Main.screen_route) { HomeView(navController, tripListViewModel, tripList) }
        composable(BottomNavItem.AddTrip.screen_route) { AddTripView(navController, tripListViewModel) }
        composable(BottomNavItem.Settings.screen_route) { SettingsView(navController) }
        composable("WelcomeView"){ WelcomeView(navController) }
        composable("TripView/{id}", arguments = listOf(navArgument("id") { type = NavType.StringType})) {
            val id = it.arguments?.getString("id")
            val trip = tripListViewModel.getTrip(UUID.fromString(id))
            TripView(navController, tripListViewModel, trip)
        }
        composable(BottomNavItem.Map.screen_route) { MapView(navController, tripListViewModel) }
    }
}