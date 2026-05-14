package com.ratanapps.consultoapp.ui.features.home.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ratanapps.consultoapp.ui.features.home.navigation.HomeRoute

@Composable
fun HomeBottomBar(bottomBarNavController: NavHostController) {
    val navBackStackEntry by bottomBarNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = HomeRoute.DASHBOARD) },
            selected = currentRoute == HomeRoute.DASHBOARD,
            onClick = {
                        bottomBarNavController.navigate(HomeRoute.DASHBOARD) {
                            popUpTo(bottomBarNavController.graph.startDestinationId){
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                      },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Gray,
                indicatorColor = Color(0xFF4392A4)
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Schedule, contentDescription = HomeRoute.BOOKING) },
            selected = currentRoute == HomeRoute.BOOKING,
            onClick = {
                        bottomBarNavController.navigate(HomeRoute.BOOKING) {
                            popUpTo(bottomBarNavController.graph.startDestinationId){
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                      },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Gray,
                indicatorColor = Color(0xFF4392A4)
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.ChatBubbleOutline, contentDescription = HomeRoute.CHAT) },
            selected = currentRoute == HomeRoute.CHAT,
            onClick = {
                        bottomBarNavController.navigate(HomeRoute.CHAT) {
                            popUpTo(bottomBarNavController.graph.startDestinationId){
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                      },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Gray,
                indicatorColor = Color(0xFF4392A4)
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.PersonOutline, contentDescription = HomeRoute.PROFILE) },
            selected = currentRoute == HomeRoute.PROFILE,
            onClick = {
                        bottomBarNavController.navigate(HomeRoute.PROFILE) {
                            popUpTo(bottomBarNavController.graph.startDestinationId){
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                      },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Gray,
                indicatorColor = Color(0xFF4392A4)
            )
        )
    }
}

