package com.example.travelplanner.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.travelplanner.model.BottomNavItem
import com.example.travelplanner.model.Trip
import com.example.travelplanner.viewmodel.MainViewModel
import java.util.*

@Composable
fun NavHostView(navController: NavHostController, viewModel: MainViewModel, tripList: List<Trip>) {
    NavHost(navController = navController, startDestination = "WelcomeView") {
        composable(BottomNavItem.Main.screen_route) { HomeView(navController, viewModel, tripList) }
        composable(BottomNavItem.AddTrip.screen_route) { AddTripView(navController, viewModel) }
        composable(BottomNavItem.Settings.screen_route) { SettingsView(navController) }
        composable("WelcomeView"){ WelcomeView(navController) }
        composable("TripView/{tripJson}", arguments = listOf(navArgument("tripJson") { type = NavType.StringType})) { backStackEntry ->
            val tripJson = backStackEntry.arguments?.getString("tripJson")
            TripView(navController, viewModel, tripJson ?: "")
        }
        composable(BottomNavItem.Map.screen_route) { MapView(navController, viewModel) }
    }
}