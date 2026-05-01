package com.ratanapps.auth.ui.feature.signup

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ratanapps.auth.R
import com.ratanapps.auth.ui.feature.signup.component.AuthTextField
import com.ratanapps.auth.ui.feature.signup.component.OrDivider
import com.ratanapps.auth.ui.feature.signup.component.SocialLoginButton

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    onSignUpClick: (String, String, String, String) -> Unit = { _, _, _, _ -> },
    onSignInClick: () -> Unit = {},
    onFacebookClick: () -> Unit = {},
    onGoogleClick: () -> Unit = {}
) {
    var fullName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
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
                value = fullName,
                onValueChange = { fullName = it },
                label = stringResource(id = R.string.full_name),
                placeholder = stringResource(id = R.string.enter_your_full_name),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
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

            Spacer(modifier = Modifier.height(24.dp))

            // Email Field
            AuthTextField(
                value = email,
                onValueChange = { email = it },
                label = stringResource(id = R.string.email),
                placeholder = stringResource(id = R.string.enter_your_email),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Mobile Number Field
            AuthTextField(
                value = mobileNumber,
                onValueChange = { mobileNumber = it },
                label = stringResource(id = R.string.mobile_number),
                placeholder = stringResource(id = R.string.enter_your_phone_number),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = { onSignUpClick(fullName, password, email, mobileNumber) },
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
                SocialLoginButton(
                    onClick = onFacebookClick
                )
                Spacer(modifier = Modifier.width(24.dp))
                SocialLoginButton(
                    onClick = onGoogleClick,
                    isGoogle = true
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.already_have_account))
                    withStyle(style = SpanStyle(color = titleColor, fontWeight = FontWeight.Bold)) {
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
}

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    SignupScreen()
}
