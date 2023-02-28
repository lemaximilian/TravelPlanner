package com.example.travelplanner.view

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.travelplanner.model.BottomNavItem
import com.example.travelplanner.viewmodel.MainViewModel


@Composable
fun NavHostView(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: MainViewModel = viewModel(
        factory = MainViewModel.Factory(context.applicationContext as Application)
    )
    val tripList = viewModel.readAllDataTrip.observeAsState(listOf()).value

    NavHost(navController = navController, startDestination = "WelcomeView") {
        composable(BottomNavItem.Main.screen_route) { HomeView(navController, viewModel, tripList)
        }
        composable(BottomNavItem.AddTrip.screen_route) { AddTripView(navController, viewModel) }
        composable(BottomNavItem.Settings.screen_route) { SettingsView(navController) }
        composable("WelcomeView") { WelcomeView(navController, viewModel) }
        composable("TripView/{tripJson}", arguments = listOf(navArgument("tripJson") { type = NavType.StringType })) { backStackEntry ->
            val tripJson = backStackEntry.arguments?.getString("tripJson")
            TripView(navController, viewModel, tripJson ?: "")
        }
        composable(BottomNavItem.Map.screen_route) { MapView(navController, viewModel) }
        composable("TravelerView/{tripJson}", arguments = listOf(navArgument("tripJson") { type = NavType.StringType })) { backStackEntry ->
            val tripJson = backStackEntry.arguments?.getString("tripJson")
            TripTraveler(navController, viewModel, tripJson ?: "")
        }
    }
}