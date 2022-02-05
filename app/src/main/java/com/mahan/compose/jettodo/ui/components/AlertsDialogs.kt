package com.mahan.compose.jettodo.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun DisplayAlertDialog(
    openDialog: Boolean,
    title: String,
    message: String,
    confirmText: String,
    cancelText: String,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    if (!openDialog) return
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = {
            Text(
                text = title,
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = message,
                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                fontWeight = FontWeight.Normal
            )
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest() }
            ) {
                Text(text = cancelText)
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm() }
            ) {
                Text(text = confirmText)
            }
        }
    )
}