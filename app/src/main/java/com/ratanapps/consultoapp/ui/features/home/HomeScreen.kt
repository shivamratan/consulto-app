package com.ratanapps.consultoapp.ui.features.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ratanapps.consultoapp.ui.features.booking.BookingScreen
import com.ratanapps.consultoapp.ui.features.chat.ChatScreen
import com.ratanapps.consultoapp.ui.features.dashboard.DashboardScreen
import com.ratanapps.consultoapp.ui.features.home.component.HomeBottomBar
import com.ratanapps.consultoapp.ui.features.home.component.HomeNavDrawerSheet
import com.ratanapps.consultoapp.ui.features.home.component.HomeTopAppBar
import com.ratanapps.consultoapp.ui.features.home.navigation.HomeRoute
import com.ratanapps.consultoapp.ui.features.profile.ProfileScreen
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(modifier: Modifier = Modifier, onLogoutClicked: ()->Unit = {}) {
    var searchQuery by remember { mutableStateOf("") }
    val bottomBarNavController: NavHostController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    ModalNavigationDrawer(modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            HomeNavDrawerSheet(
                onLogoutClicked = onLogoutClicked
            )
        },
        content = {
            Scaffold(
                modifier = modifier,
                bottomBar = { HomeBottomBar(bottomBarNavController) },
                topBar = { HomeTopAppBar(onHamburgerClick = {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed)
                                open()
                            else
                                close()
                        }
                    }
                }) }
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
        )

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
