package com.ratanapps.auth.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ratanapps.auth.ui.feature.login.LoginScreen
import com.ratanapps.auth.ui.feature.signup.SignupScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    onLoginSuccess: () -> Unit
) {
    composable(AuthRoutes.LOGIN) {
        LoginScreen(
            onSignUpClick = {
                navController.navigate(AuthRoutes.SIGNUP)
            }
        )
    }

    composable(AuthRoutes.SIGNUP) {
        SignupScreen(
            onSignInClick = {
                navController.popBackStack()
            }
        )
    }

}