package com.ratanapps.consultoapp.ui.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.ratanapps.consultoapp.ui.features.home.component.*
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ratanapps.consultoapp.ui.features.booking.BookingScreen
import com.ratanapps.consultoapp.ui.features.chat.ChatScreen
import com.ratanapps.consultoapp.ui.features.dashboard.DashboardScreen
import com.ratanapps.consultoapp.ui.features.home.navigation.HomeRoute
import com.ratanapps.consultoapp.ui.features.profile.ProfileScreen

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") }
    val bottomBarNavController: NavHostController = rememberNavController()

    Scaffold(
        modifier = modifier,
        bottomBar = { HomeBottomBar(bottomBarNavController) }
    ) { paddingValues ->

        NavHost(
            navController = bottomBarNavController,
            startDestination = "dashboard",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(HomeRoute.DASHBOARD) {
                DashboardScreen()
            }
            composable(HomeRoute.BOOKING) {
                BookingScreen()
            }
            composable(HomeRoute.CHAT) {
                ChatScreen()
            }
            composable(HomeRoute.PROFILE) {
                ProfileScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
