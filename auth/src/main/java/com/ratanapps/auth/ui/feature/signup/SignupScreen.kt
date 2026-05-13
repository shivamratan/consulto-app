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
import com.ratanapps.auth.ui.feature.signup.component.AuthHeader
import com.ratanapps.auth.ui.feature.signup.component.LoadingOverlay
import com.ratanapps.auth.ui.feature.signup.component.OrDivider
import com.ratanapps.auth.ui.feature.signup.component.SocialLoginButton
import com.ratanapps.auth.ui.feature.signup.viewmodel.SignUpUIState
import com.ratanapps.auth.ui.feature.signup.viewmodel.SignupViewModel
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
 * Main Composable for the Signup Screen.
 *
 * This screen provides the user interface for creating a new account, including:
 * - Form fields for username, email, and password.
 * - Social login options (Google).
 * - Navigation link back to the Sign In screen.
 * - Loading overlay and error handling via Snackbars and Toasts.
 *
 * @param modifier Modifier for the root layout.
 * @param viewModel The [SignupViewModel] handling business logic and state.
 * @param onSignInClick Callback triggered when the user wants to navigate to the Login screen.
 * @param onSuccessSignup Callback triggered after a successful account creation.
 */
@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    viewModel: SignupViewModel,
    onSignInClick: () -> Unit = {},
    onSuccessSignup: () -> Unit = {},
    onGoogleSignupSuccess: () -> Unit = {}
) {
    val uiState by viewModel.signupUIState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    // Observe state changes and trigger one-time UI events
    SignupSideEffects(uiState, viewModel, snackbarHostState, onSuccessSignup, onGoogleSignupSuccess)

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (uiState.isLoading) {
                LoadingOverlay()
            } else {
                SignupContent(
                    uiState = uiState,
                    viewModel = viewModel,
                    onSignInClick = onSignInClick,
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
 * Manages side effects such as displaying error Toasts, success messages,
 * and showing Snackbars for Google Account errors.
 *
 * @param uiState The current [SignUpUIState] from the ViewModel.
 * @param viewModel The [SignupViewModel] for dismissing error states.
 * @param snackbarHostState State for controlling the [SnackbarHost].
 * @param onSuccessSignup Action to take when signup is successful.
 */
@Composable
private fun SignupSideEffects(
    uiState: SignUpUIState,
    viewModel: SignupViewModel,
    snackbarHostState: SnackbarHostState,
    onSuccessSignup: () -> Unit,
    onGoogleSignupSuccess: () -> Unit = {}
) {
    val context = LocalContext.current

    // Success State Side Effect
    LaunchedEffect(uiState.isSuccessfulSignUp) {
        if (uiState.isSuccessfulSignUp) {
            AuthUtils.showToast(context, "Signup Successful, Now Login !")
            onSuccessSignup()
        }
    }

    LaunchedEffect(uiState.isGoogleSignupSuccess) {
        if (uiState.isGoogleSignupSuccess) {
            AuthUtils.showToast(context, "Google Signup Successful !")
            onGoogleSignupSuccess()
        }
    }



    // Failure State Side Effect
    LaunchedEffect(uiState.isSignUpFailed) {
        if (uiState.isSignUpFailed) {
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
}

/**
 * Houses the layout structure of the Signup screen, including the header, form, and actions.
 *
 * @param uiState Current state of the signup process.
 * @param viewModel ViewModel for handling input changes and actions.
 * @param onSignInClick Callback for navigating to the sign-in screen.
 * @param onGoogleSignInClick Callback for initiating Google sign-in.
 */
@Composable
private fun SignupContent(
    uiState: SignUpUIState,
    viewModel: SignupViewModel,
    onSignInClick: () -> Unit,
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
            title = stringResource(id = R.string.sign_up),
            subtitle = stringResource(id = R.string.create_new_account)
        )

        Spacer(modifier = Modifier.height(15.dp))

        SignupForm(uiState, viewModel)

        Spacer(modifier = Modifier.height(30.dp))
        
        SignupActions(
            onSignupClick = viewModel::signup,
            onGoogleClick = onGoogleSignInClick
        )

        Spacer(modifier = Modifier.height(12.dp))
        SignupFooter(onSignInClick)
        Spacer(modifier = Modifier.height(32.dp))
    }
}

/**
 * Renders the input fields for the signup process.
 * Uses [ValidatedField] to automatically handle error messaging.
 */
@Composable
private fun SignupForm(uiState: SignUpUIState, viewModel: SignupViewModel) {
    ValidatedField(
        value = uiState.username,
        onValueChange = viewModel::onUserNameChange,
        label = stringResource(R.string.full_name),
        placeholder = stringResource(R.string.enter_your_full_name),
        errorText = uiState.usernameError,
        isErrorVisible = !uiState.isValid
    )

    Spacer(modifier = Modifier.height(24.dp))

    ValidatedField(
        value = uiState.email,
        onValueChange = viewModel::onEmailChange,
        label = stringResource(R.string.email),
        placeholder = stringResource(R.string.enter_your_email),
        errorText = uiState.emailError,
        isErrorVisible = !uiState.isValid,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )

    Spacer(modifier = Modifier.height(24.dp))

    ValidatedField(
        value = uiState.password,
        onValueChange = viewModel::onPasswordChange,
        label = stringResource(R.string.password),
        placeholder = stringResource(R.string.enter_your_password),
        errorText = uiState.passwordError,
        isErrorVisible = !uiState.isValid,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = viewModel::togglePasswordVisibility) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        if (uiState.isPasswordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                    ),
                    contentDescription = if (uiState.isPasswordVisible) "Hide password" else "Show password",
                    tint = TitleColor
                )
            }
        }
    )
}

/**
 * Renders the main action buttons: the "Sign Up" button and social login options.
 *
 * @param onSignupClick Callback for the primary signup button.
 * @param onGoogleClick Callback for the Google social login button.
 */
@Composable
private fun SignupActions(onSignupClick: () -> Unit, onGoogleClick: () -> Unit) {
    Button(
        onClick = onSignupClick,
        modifier = Modifier.fillMaxWidth().height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = TitleColor)
    ) {
        Text(
            text = stringResource(R.string.sign_up),
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
 * Renders the clickable footer text linking back to the Sign In screen.
 *
 * @param onSignInClick Callback for navigation to the sign-in screen.
 */
@Composable
private fun SignupFooter(onSignInClick: () -> Unit) {
    val annotatedText = buildAnnotatedString {
        append(stringResource(R.string.already_have_account))
        withStyle(SpanStyle(color = TitleColor, fontWeight = FontWeight.Bold)) {
            append(stringResource(R.string.sign_in))
        }
    }
    Text(
        text = annotatedText,
        modifier = Modifier.padding(bottom = 32.dp).clickable { onSignInClick() },
        fontSize = 14.sp,
        color = TextSecondaryColor
    )
}
