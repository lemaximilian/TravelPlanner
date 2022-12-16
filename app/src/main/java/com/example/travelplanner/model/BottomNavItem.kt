package com.example.travelplanner.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title:String, var icon:ImageVector, var screen_route:String) {
    object Main : BottomNavItem("Home", Icons.Filled.Home,"home")
    object AddTrip: BottomNavItem("Reise hinzufügen",Icons.Filled.Add,"add")
    object Settings: BottomNavItem("Einstellungen",Icons.Filled.Settings,"settings")
}
