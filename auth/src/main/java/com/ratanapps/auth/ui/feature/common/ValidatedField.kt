package com.ratanapps.auth.ui.feature.common.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ratanapps.auth.ui.feature.login.component.AuthTextField

/**
 * A wrapper around AuthTextField that adds support for displaying validation error messages
 * directly below the input field.
 *
 * @param value Current text value in the field.
 * @param onValueChange Callback when text changes.
 * @param label The label text shown above the field.
 * @param placeholder The placeholder text shown inside the field when empty.
 * @param errorText The error message to display if validation fails.
 * @param isErrorVisible Whether the error message should be currently visible.
 * @param keyboardOptions Configuration for the software keyboard.
 * @param visualTransformation Controls how the input text is visually represented (e.g., password masking).
 * @param trailingIcon Optional icon shown at the end of the input field.
 */
@Composable
fun ValidatedField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    errorText: String?,
    isErrorVisible: Boolean,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        AuthTextField(
            value = value,
            onValueChange = onValueChange,
            label = label,
            placeholder = placeholder,
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon
        )
        if (isErrorVisible && errorText != null) {
            Text(
                text = errorText,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 10.dp, top = 4.dp)
            )
        }
    }
}
