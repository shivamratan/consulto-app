package com.ratanapps.auth.ui.feature.login

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ratanapps.auth.R
import com.ratanapps.auth.ui.feature.login.component.AuthHeader
import com.ratanapps.auth.ui.feature.login.component.AuthTextField
import com.ratanapps.auth.ui.feature.login.component.LoadingOverlay
import com.ratanapps.auth.ui.feature.login.component.OrDivider
import com.ratanapps.auth.ui.feature.login.component.SocialLoginButton
import com.ratanapps.auth.ui.feature.login.viewmodel.LoginViewModel
import com.ratanapps.auth.utils.AuthUtils

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    onForgetPasswordClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onLoginSuccess: () -> Unit = {}
) {

    val logInUIState by viewModel.loginUIState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // Create the state for the snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    // Logic to watch the StateFlow and show Snackbar
    LaunchedEffect(logInUIState.showNoAccountError) {
        if (logInUIState.showNoAccountError) {
            val result = snackbarHostState.showSnackbar(
                message = "No Google account found on device",
                actionLabel = "Add Account",
                duration = SnackbarDuration.Long
            )

            // Tell ViewModel to reset the state so it doesn't show again on rotation
            viewModel.dismissNoAccountError()

            if (result == SnackbarResult.ActionPerformed) {
                val intent = Intent(Settings.ACTION_ADD_ACCOUNT).apply {
                    putExtra(android.provider.Settings.EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
                }
                context.startActivity(intent)
            }
        }
    }

    LaunchedEffect(logInUIState.showNoLoginRecordFound) {
        if (logInUIState.showNoLoginRecordFound) {
            val result = snackbarHostState.showSnackbar(
                message = "No user exist with Email Address",
                actionLabel = "SignUp Now",
                duration = SnackbarDuration.Long
            )

            // Tell ViewModel to reset the state so it doesn't show again on rotation
            viewModel.dismissNoLoginRecordFoundError()

            if (result == SnackbarResult.ActionPerformed) {
                onSignUpClick.invoke()
            }
        }
    }

    val titleColor = Color(0xFF3A86B7)
    val textColor = Color(0xFF6B7280)

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White
    ) {
        Box(modifier = modifier.fillMaxSize()) {

            if (logInUIState.isSuccessfulLogin) {
                AuthUtils.showToast(context, "Login Successful, Welcome !")
                onLoginSuccess.invoke()
            } else if (logInUIState.isLoading) {
                LoadingOverlay()
            } else {

                if (logInUIState.isLoginFailed) {
                    AuthUtils.showToast(context, logInUIState.error ?: "Something went wrong")
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(48.dp))

                    AuthHeader(
                        title = stringResource(id = R.string.sign_in),
                        subtitle = stringResource(id = R.string.login_subtitle)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Email Field
                    AuthTextField(
                        value = logInUIState.email,
                        onValueChange = viewModel::onEmailChange,
                        label = stringResource(id = R.string.email),
                        placeholder = stringResource(id = R.string.enter_your_email),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                    if (logInUIState.emailError != null && logInUIState.isValid.not()) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(start = 10.dp, top = 5.dp),
                            text = logInUIState.emailError ?: "",
                            color = Color.Red
                        )
                    }


                    Spacer(modifier = Modifier.height(24.dp))

                    // Password Field
                    AuthTextField(
                        value = logInUIState.password,
                        onValueChange = viewModel::onPasswordChange,
                        label = stringResource(id = R.string.password),
                        placeholder = stringResource(id = R.string.enter_your_password),
                        trailingIcon = {
                            IconButton(onClick = viewModel::togglePasswordVisibility) {
                                Icon(
                                    imageVector = if (logInUIState.isPasswordVisible) {
                                        ImageVector.vectorResource(id = R.drawable.ic_visibility)
                                    } else {
                                        ImageVector.vectorResource(id = R.drawable.ic_visibility_off)
                                    },
                                    contentDescription = if (logInUIState.isPasswordVisible) "Hide password" else "Show password",
                                    tint = titleColor
                                )
                            }
                        },
                        visualTransformation = if (logInUIState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )

                    if (logInUIState.passwordError != null && logInUIState.isValid.not()) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(start = 10.dp, top = 5.dp),
                            text = logInUIState.passwordError ?: "",
                            color = Color.Red
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(id = R.string.forget_password),
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable { onForgetPasswordClick() },
                        color = Color(0xFF111827),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        onClick = { viewModel.login() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = titleColor)
                    ) {
                        Text(
                            text = stringResource(id = R.string.sign_in),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(38.dp))

                    OrDivider()

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        /*SocialLoginButton(
                    onClick = onFacebookClick
                )
                Spacer(modifier = Modifier.width(24.dp))*/
                        SocialLoginButton(
                            onClick = { viewModel.onGoogleSignInClick(context) },
                            isGoogle = true
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(id = R.string.dont_have_account))
                            withStyle(
                                style = SpanStyle(
                                    color = titleColor,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append(stringResource(id = R.string.sign_up))
                            }
                        },
                        modifier = Modifier
                            .padding(bottom = 32.dp)
                            .clickable { onSignUpClick() },
                        fontSize = 14.sp,
                        color = textColor
                    )
                }
            }


            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp) // Adjust padding so it doesn't overlap UI elements
            )
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}*/
