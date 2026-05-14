package com.ratanapps.consultoapp.ui.features.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

/**
 * A reusable alert dialog component for the Notes app.
 * Used for confirmations, such as when deleting a note.
 *
 * @param onDismissRequest Callback triggered when the dialog is dismissed or canceled.
 * @param onConfirmation Callback triggered when the confirm button is clicked.
 * @param dialogTitle The title text to display at the top of the dialog.
 * @param dialogText The main message body of the dialog.
 */
@Composable
fun ConsultoAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}