package com.ratanapps.consultoapp.ui.features.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ratanapps.consultoapp.ui.HomeViewModel
import com.ratanapps.consultoapp.ui.features.booking.BookingScreen
import com.ratanapps.consultoapp.ui.features.chat.ChatScreen
import com.ratanapps.consultoapp.ui.features.common.ConsultoAlertDialog
import com.ratanapps.consultoapp.ui.features.dashboard.DashboardScreen
import com.ratanapps.consultoapp.ui.features.home.component.HomeBottomBar
import com.ratanapps.consultoapp.ui.features.home.component.HomeFab
import com.ratanapps.consultoapp.ui.features.home.component.HomeNavDrawerSheet
import com.ratanapps.consultoapp.ui.features.home.component.HomeTopAppBar
import com.ratanapps.consultoapp.ui.features.home.navigation.HomeRoute
import com.ratanapps.consultoapp.ui.features.profile.ProfileScreen
import com.ratanapps.consultoapp.ui.utils.ComposeUtils
import com.ratanapps.consultoapp.utils.AppUtils
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(modifier: Modifier = Modifier, activityViewModel: HomeViewModel, onLogoutClicked: ()->Unit = {}) {

    val bottomBarNavController: NavHostController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ModalNavigationDrawer(modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            HomeNavDrawerSheet(
                onLogoutClicked = {
                    scope.launch {
                        if (drawerState.isOpen)
                            drawerState.close()
                    }
                    activityViewModel.showLogOutConfirmDialog.value = true
                }
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
                }) },
                floatingActionButton = {
                    HomeFab() {
                        AppUtils.showShortToast(context, "Clicked")
                    }
                }
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

                if (activityViewModel.showLogOutConfirmDialog.value) {
                    ConsultoAlertDialog(
                        onDismissRequest = { activityViewModel.showLogOutConfirmDialog.value = false },
                        onConfirmation = {
                            activityViewModel.showLogOutConfirmDialog.value = false
                            onLogoutClicked.invoke()
                        },
                        dialogTitle = "Log out",
                        dialogText = "Are you sure want to logout ?"
                    )
                }

            }
        }
        )

}

/*@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}*/
