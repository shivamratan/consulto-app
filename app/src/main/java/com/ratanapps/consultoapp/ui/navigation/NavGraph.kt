package com.ratanapps.consultoapp.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.ratanapps.auth.ui.navigation.AuthRoutes
import com.ratanapps.auth.ui.navigation.authNavGraph
import com.ratanapps.auth.ui.util.ComposeUtil
import com.ratanapps.consultoapp.ui.HomeViewModel
import com.ratanapps.consultoapp.ui.utils.ComposeUtils

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    activityViewModel: HomeViewModel
) {

    val currentUser = FirebaseAuth.getInstance().currentUser
    val startDestination = if (currentUser != null) {
        "home"
    } else {
        "login"
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable("home") {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = modifier.fillMaxSize()) {
                    Text(text = "Welcome to ConsultoApp")
                    Spacer(modifier = Modifier.height(10.dp))

                    Button(onClick = {
                        activityViewModel.logout()
                        navController.navigate("login") {
                            popUpTo("home") {
                                inclusive = true
                            }
                        }
                    }) {
                        Text(text = "Logout")
                    }
                }

            }
        }

        authNavGraph(
            navController = navController,
            onLoginSuccess = {
                navController.navigate("home") {
                    popUpTo(AuthRoutes.LOGIN) {
                        inclusive = true
                    }
                }

            },
            onGoogleSignupSuccess = {
                navController.navigate("home") {
                    popUpTo(AuthRoutes.SIGNUP) {
                        inclusive = true
                    }
                }
            }
        )
    }
}
