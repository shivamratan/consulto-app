package com.ratanapps.auth.ui.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ratanapps.auth.ui.feature.login.LoginScreen
import com.ratanapps.auth.ui.feature.signup.SignupScreen
import com.ratanapps.auth.ui.feature.signup.viewmodel.SignupViewModel
import com.ratanapps.auth.utils.AuthUtils

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
        val signupViewModel: SignupViewModel  = hiltViewModel()

        SignupScreen(
            onSignInClick = {
                navController.popBackStack()
            },
            viewModel = signupViewModel,
            onSuccessSignup = {
                navController.popBackStack()
            }
        )
    }

}