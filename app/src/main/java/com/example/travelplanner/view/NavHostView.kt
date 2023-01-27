package com.example.travelplanner.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.travelplanner.model.BottomNavItem
import com.example.travelplanner.viewmodel.TripListViewModel

@Composable
fun NavHostView(navController: NavHostController, tripListViewModel: TripListViewModel) {
    NavHost(navController = navController, startDestination = "WelcomeView") {
        composable(BottomNavItem.Main.screen_route) { HomeView(navController, tripListViewModel) }
        composable(BottomNavItem.AddTrip.screen_route) { AddTripView(navController, tripListViewModel) }
        composable(BottomNavItem.Settings.screen_route) { SettingsView(navController) }
        composable("WelcomeView",){ WelcomeView(navController)}

    }
}