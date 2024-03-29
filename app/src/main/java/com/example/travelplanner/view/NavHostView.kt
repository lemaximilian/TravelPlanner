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
import com.example.travelplanner.viewmodel.ExpensesViewModel
import com.example.travelplanner.viewmodel.MainViewModel
import com.example.travelplanner.viewmodel.TravelerViewModel
import com.example.travelplanner.viewmodel.TodoViewModel


@Composable
fun NavHostView(navController: NavHostController) {
    val context = LocalContext.current

    // creating viewmodel instances
    val todoview : TodoViewModel = viewModel(
        factory = TodoViewModel.Factory(context.applicationContext as Application)
    )
    val viewModel: MainViewModel = viewModel(
        factory = MainViewModel.Factory(context.applicationContext as Application)
    )
    val travelerViewModel: TravelerViewModel = viewModel(
        factory = TravelerViewModel.Factory(context.applicationContext as Application)
    )
    val expensesViewModel: ExpensesViewModel = viewModel(
        factory = ExpensesViewModel.Factory(context.applicationContext as Application)
    )

    //navgraph, pass navcontroller and viewmodels to views
    NavHost(navController = navController, startDestination = "WelcomeView") {
        composable(BottomNavItem.Main.screen_route) { HomeView(navController, viewModel) }
        composable(BottomNavItem.Settings.screen_route) { SettingsView(navController) }
        composable("WelcomeView") { WelcomeView(navController, viewModel) }
        composable("TripView/{tripJson}", arguments = listOf(navArgument("tripJson") { type = NavType.StringType })) { backStackEntry ->
            // get encoded trip from backstack to pass it as argument to view (decoding in view)
            val tripJson = backStackEntry.arguments?.getString("tripJson")
            TripView(navController, viewModel, travelerViewModel, expensesViewModel, todoview, tripJson ?: "")
        }
        //composable("MapView") { MapView(navController, viewModel) }
        composable("TravelerView/{tripJson}", arguments = listOf(navArgument("tripJson") { type = NavType.StringType })) { backStackEntry ->
            val tripJson = backStackEntry.arguments?.getString("tripJson")
            TravelerView(navController, travelerViewModel, tripJson ?: "")
        }
        composable("ExpensesView/{tripJson}", arguments = listOf(navArgument("tripJson") { type = NavType.StringType })) { backStackEntry ->
            val tripJson = backStackEntry.arguments?.getString("tripJson")
            ExpensesView(navController, expensesViewModel, tripJson ?: "")
        }
        composable("ToDoView/{tripJson}", arguments = listOf(navArgument("tripJson") { type = NavType.StringType })) { backStackEntry ->
            val tripJson = backStackEntry.arguments?.getString("tripJson")
            ToDoView(navController, tripJson ?: "",todoview)
        }
    }
}