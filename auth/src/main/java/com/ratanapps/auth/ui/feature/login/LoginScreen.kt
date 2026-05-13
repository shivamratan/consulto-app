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
import androidx.compose.foundation.layout.wrapContentWidth
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
import com.ratanapps.auth.ui.feature.common.component.ValidatedField
import com.ratanapps.auth.ui.feature.login.component.AuthHeader
import com.ratanapps.auth.ui.feature.login.component.LoadingOverlay
import com.ratanapps.auth.ui.feature.login.component.OrDivider
import com.ratanapps.auth.ui.feature.login.component.SocialLoginButton
import com.ratanapps.auth.ui.feature.login.viewmodel.LogInUIState
import com.ratanapps.auth.ui.feature.login.viewmodel.LoginViewModel
import com.ratanapps.auth.utils.AuthUtils

/**
 * Primary branding color used for titles and buttons.
 */
private val TitleColor = Color(0xFF3A86B7)

/**
 * Secondary text color used for subtitles and hints.
 */
private val TextSecondaryColor = Color(0xFF6B7280)

/**
 * Main Composable for the Login Screen.
 *
 * This screen provides the user interface for authenticating existing users, including:
 * - Email and Password input fields.
 * - Social login options (Google).
 * - "Forgot Password" link.
 * - Navigation link to the Sign Up screen.
 * - Loading states and error handling via Snackbars and Toasts.
 *
 * @param modifier Modifier for the root layout.
 * @param viewModel The [LoginViewModel] handling business logic and state.
 * @param onForgetPasswordClick Callback triggered when the user clicks "Forget Password".
 * @param onSignUpClick Callback triggered when the user wants to navigate to the Signup screen.
 * @param onLoginSuccess Callback triggered after a successful login.
 */


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    onForgetPasswordClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onLoginSuccess: () -> Unit = {}
) {
    val uiState by viewModel.loginUIState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    // Observe state changes and trigger one-time UI events
    LoginSideEffects(uiState, viewModel, snackbarHostState, onSignUpClick, onLoginSuccess)

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (uiState.isLoading) {
                LoadingOverlay()
            } else {
                LoginContent(
                    uiState = uiState,
                    viewModel = viewModel,
                    onForgetPasswordClick = onForgetPasswordClick,
                    onSignUpClick = onSignUpClick,
                    onGoogleSignInClick = { viewModel.onGoogleSignInClick(context) }
                )
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
            )
        }
    }
}

/**
 * Manages side effects for the Login screen such as Toasts and Snackbar messages.
 */
@Composable
private fun LoginSideEffects(
    uiState: LogInUIState,
    viewModel: LoginViewModel,
    snackbarHostState: SnackbarHostState,
    onSignUpClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current

    // Success State Side Effect
    LaunchedEffect(uiState.isSuccessfulLogin) {
        if (uiState.isSuccessfulLogin) {
            AuthUtils.showToast(context, "Login Successful, Welcome !")
            onLoginSuccess()
        }
    }

    // Failure State Side Effect
    LaunchedEffect(uiState.isLoginFailed) {
        if (uiState.isLoginFailed) {
            AuthUtils.showToast(context, uiState.error ?: "Something went wrong")
        }
    }

    // Google Account Error Side Effect
    LaunchedEffect(uiState.showNoAccountError) {
        if (uiState.showNoAccountError) {
            val result = snackbarHostState.showSnackbar(
                message = "No Google account found on device",
                actionLabel = "Add Account",
                duration = SnackbarDuration.Long
            )
            viewModel.dismissNoAccountError()
            if (result == SnackbarResult.ActionPerformed) {
                val intent = Intent(Settings.ACTION_ADD_ACCOUNT).apply {
                    putExtra(Settings.EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
                }
                context.startActivity(intent)
            }
        }
    }

    // No Login Record Found Side Effect
    LaunchedEffect(uiState.showNoLoginRecordFound) {
        if (uiState.showNoLoginRecordFound) {
            val result = snackbarHostState.showSnackbar(
                message = "No user exist with Email Address",
                actionLabel = "SignUp Now",
                duration = SnackbarDuration.Long
            )
            viewModel.dismissNoLoginRecordFoundError()
            if (result == SnackbarResult.ActionPerformed) {
                onSignUpClick()
            }
        }
    }
}

/**
 * Houses the layout structure of the Login screen.
 */
@Composable
private fun LoginContent(
    uiState: LogInUIState,
    viewModel: LoginViewModel,
    onForgetPasswordClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onGoogleSignInClick: () -> Unit
) {
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

        LoginForm(uiState, viewModel, onForgetPasswordClick)

        Spacer(modifier = Modifier.height(30.dp))

        LoginActions(
            onLoginClick = viewModel::login,
            onGoogleClick = onGoogleSignInClick
        )

        Spacer(modifier = Modifier.height(12.dp))
        LoginFooter(onSignUpClick)
        Spacer(modifier = Modifier.height(32.dp))
    }
}

/**
 * Renders the input fields and "Forgot Password" link for the login process.
 */
@Composable
private fun LoginForm(
    uiState: LogInUIState,
    viewModel: LoginViewModel,
    onForgetPasswordClick: () -> Unit
) {
    // Email Field
    ValidatedField(
        value = uiState.email,
        onValueChange = viewModel::onEmailChange,
        label = stringResource(id = R.string.email),
        placeholder = stringResource(id = R.string.enter_your_email),
        errorText = uiState.emailError,
        isErrorVisible = !uiState.isValid,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )

    Spacer(modifier = Modifier.height(24.dp))

    // Password Field
    ValidatedField(
        value = uiState.password,
        onValueChange = viewModel::onPasswordChange,
        label = stringResource(id = R.string.password),
        placeholder = stringResource(id = R.string.enter_your_password),
        errorText = uiState.passwordError,
        isErrorVisible = !uiState.isValid,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = viewModel::togglePasswordVisibility) {
                Icon(
                    imageVector = if (uiState.isPasswordVisible) {
                        ImageVector.vectorResource(id = R.drawable.ic_visibility)
                    } else {
                        ImageVector.vectorResource(id = R.drawable.ic_visibility_off)
                    },
                    contentDescription = if (uiState.isPasswordVisible) "Hide password" else "Show password",
                    tint = TitleColor
                )
            }
        }
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = stringResource(id = R.string.forget_password),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.End)
            .clickable { onForgetPasswordClick() },
        color = Color(0xFF111827),
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold
    )
}

/**
 * Renders the primary login actions.
 */
@Composable
private fun LoginActions(onLoginClick: () -> Unit, onGoogleClick: () -> Unit) {
    Button(
        onClick = onLoginClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = TitleColor)
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
        SocialLoginButton(
            onClick = onGoogleClick,
            isGoogle = true
        )
    }
}

/**
 * Renders the footer text with a link to the Sign Up screen.
 */
@Composable
private fun LoginFooter(onSignUpClick: () -> Unit) {
    val annotatedText = buildAnnotatedString {
        append(stringResource(id = R.string.dont_have_account))
        withStyle(SpanStyle(color = TitleColor, fontWeight = FontWeight.Bold)) {
            append(stringResource(id = R.string.sign_up))
        }
    }
    Text(
        text = annotatedText,
        modifier = Modifier
            .padding(bottom = 32.dp)
            .clickable { onSignUpClick() },
        fontSize = 14.sp,
        color = TextSecondaryColor
    )
}
