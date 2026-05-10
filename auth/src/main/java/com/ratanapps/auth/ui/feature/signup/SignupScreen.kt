package com.ratanapps.auth.ui.feature.signup

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ratanapps.auth.R
import com.ratanapps.auth.ui.feature.signup.component.AuthTextField
import com.ratanapps.auth.ui.feature.signup.component.LoadingOverlay
import com.ratanapps.auth.ui.feature.signup.component.OrDivider
import com.ratanapps.auth.ui.feature.signup.component.SocialLoginButton
import com.ratanapps.auth.ui.feature.signup.viewmodel.SignupViewModel
import com.ratanapps.auth.ui.googleauth.GoogleAuthUIProvider
import com.ratanapps.auth.utils.AuthUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    viewModel: SignupViewModel,
    onSignInClick: () -> Unit = {},
    onSuccessSignup: () -> Unit = {}
) {

    val signupUIState by viewModel.signupUIState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // Create the state for the snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    // Logic to watch the StateFlow and show Snackbar
    LaunchedEffect(signupUIState.showNoAccountError) {
        if (signupUIState.showNoAccountError) {
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

    val titleColor = Color(0xFF3A86B7)
    val textColor = Color(0xFF6B7280)

        Surface(
            modifier = modifier
                .fillMaxSize()
                ,
            color = Color.White
        ) {
            Box(modifier = Modifier.fillMaxSize())
            {
                if (signupUIState.isSuccessfulSignUp) {
                    AuthUtils.showToast(context, "Signup Successful, Now Login !")
                    onSuccessSignup.invoke()
                } else if (signupUIState.isLoading) {
                    LoadingOverlay()
                } else {

                    if (signupUIState.isSignUpFailed) {
                        AuthUtils.showToast(context, signupUIState.error ?: "Something went wrong")
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(48.dp))

                        Text(
                            text = stringResource(id = R.string.create_new_account),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = titleColor,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // Full Name Field
                        AuthTextField(
                            value = signupUIState.username,
                            onValueChange = viewModel::onUserNameChange,
                            label = stringResource(id = R.string.full_name),
                            placeholder = stringResource(id = R.string.enter_your_full_name),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )

                        if (signupUIState.usernameError != null && signupUIState.isValid.not()) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .padding(start = 10.dp, top = 5.dp),
                                text = signupUIState.usernameError ?: "",
                                color = Color.Red
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Password Field
                        AuthTextField(
                            value = signupUIState.password,
                            onValueChange = viewModel::onPasswordChange,
                            label = stringResource(id = R.string.password),
                            placeholder = stringResource(id = R.string.enter_your_password),
                            trailingIcon = {
                                IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                                    Icon(
                                        imageVector = if (signupUIState.isPasswordVisible) {
                                            ImageVector.vectorResource(id = R.drawable.ic_visibility)
                                        } else {
                                            ImageVector.vectorResource(id = R.drawable.ic_visibility_off)
                                        },
                                        contentDescription = if (signupUIState.isPasswordVisible) "Hide password" else "Show password",
                                        tint = titleColor
                                    )
                                }
                            },
                            visualTransformation = if (signupUIState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )

                        if (signupUIState.passwordError != null && signupUIState.isValid.not()) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .padding(start = 10.dp, top = 5.dp),
                                text = signupUIState.passwordError ?: "",
                                color = Color.Red
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Email Field
                        AuthTextField(
                            value = signupUIState.email,
                            onValueChange = viewModel::onEmailChange,
                            label = stringResource(id = R.string.email),
                            placeholder = stringResource(id = R.string.enter_your_email),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )

                        if (signupUIState.emailError != null && signupUIState.isValid.not()) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .padding(start = 10.dp, top = 5.dp),
                                text = signupUIState.emailError ?: "",
                                color = Color.Red
                            )
                        }

                        Spacer(modifier = Modifier.height(48.dp))

                        Button(
                            onClick = { viewModel.signup() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = titleColor)
                        ) {
                            Text(
                                text = stringResource(id = R.string.sign_up),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        OrDivider()

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            /*  SocialLoginButton(onClick = onFacebookClick)
                        Spacer(modifier = Modifier.width(24.dp))  */

                            SocialLoginButton(
                                onClick = { viewModel.onGoogleSignInClick(context) },
                                isGoogle = true
                            )
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Text(
                            text = buildAnnotatedString {
                                append(stringResource(id = R.string.already_have_account))
                                withStyle(
                                    style = SpanStyle(
                                        color = titleColor,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append(stringResource(id = R.string.sign_in))
                                }
                            },
                            modifier = Modifier
                                .padding(bottom = 32.dp)
                                .clickable { onSignInClick() },
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
fun SignupScreenPreview() {
    SignupScreen()
}*/
