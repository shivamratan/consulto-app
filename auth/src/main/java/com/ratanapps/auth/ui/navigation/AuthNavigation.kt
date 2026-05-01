package com.ratanapps.auth.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ratanapps.auth.ui.feature.login.LoginScreen

fun NavGraphBuilder.authNavGraph(
    onLoginSuccess: () -> Unit
) {
    composable("login") {
        LoginScreen()
    }
}