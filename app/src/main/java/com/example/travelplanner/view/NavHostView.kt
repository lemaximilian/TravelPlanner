package com.example.travelplanner.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.travelplanner.model.BottomNavItem

@Composable
fun NavHostView(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable(BottomNavItem.Main.screen_route) { HomeView(navController) }
        composable(BottomNavItem.AddTrip.screen_route) { AddTripView(navController) }
        composable(BottomNavItem.Settings.screen_route) { SettingsView(navController) }
    }
}