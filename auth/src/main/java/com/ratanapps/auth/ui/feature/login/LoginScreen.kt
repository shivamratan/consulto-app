package com.ratanapps.auth.ui.feature.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ratanapps.auth.R
import com.ratanapps.auth.ui.feature.login.component.AuthHeader
import com.ratanapps.auth.ui.feature.login.component.AuthTextField
import com.ratanapps.auth.ui.feature.login.component.OrDivider
import com.ratanapps.auth.ui.feature.login.component.SocialLoginButton

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onSignInClick: (String, String) -> Unit = { _, _ -> },
    onForgetPasswordClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onFacebookClick: () -> Unit = {},
    onGoogleClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val titleColor = Color(0xFF3A86B7)
    val textColor = Color(0xFF6B7280)

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White
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

            // Email Field
            AuthTextField(
                value = email,
                onValueChange = { email = it },
                label = stringResource(id = R.string.email),
                placeholder = stringResource(id = R.string.enter_your_email),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Password Field
            AuthTextField(
                value = password,
                onValueChange = { password = it },
                label = stringResource(id = R.string.password),
                placeholder = stringResource(id = R.string.enter_your_password),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = if (isPasswordVisible) {
                                ImageVector.vectorResource(id = R.drawable.ic_visibility)
                            } else {
                                ImageVector.vectorResource(id = R.drawable.ic_visibility_off)
                            },
                            contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                            tint = titleColor
                        )
                    }
                },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

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
                onClick = { onSignInClick(email, password) },
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

            Spacer(modifier = Modifier.height(24.dp))

            OrDivider()

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                SocialLoginButton(
                    onClick = onFacebookClick
                )
                Spacer(modifier = Modifier.width(24.dp))
                SocialLoginButton(
                    onClick = onGoogleClick,
                    isGoogle = true
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.dont_have_account))
                    withStyle(style = SpanStyle(color = titleColor, fontWeight = FontWeight.Bold)) {
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
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
