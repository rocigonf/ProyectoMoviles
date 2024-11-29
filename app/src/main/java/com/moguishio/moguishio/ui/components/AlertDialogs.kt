package com.moguishio.moguishio.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign

@Composable
fun AlertDialogMenu(
        dialogText: String,
        textAccept: String,
        textCancel: String,
        onDismissRequest: () -> Unit,
        onConfirmation: () -> Unit,
    ){
    AlertDialog(
        text = {
            EstablecerTexto(text = dialogText, textAlign = TextAlign.Center)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(onClick = { onConfirmation() } ) {
                EstablecerTexto(text = textAccept, textAlign = TextAlign.Center)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() } ) {
                EstablecerTexto(text = textCancel, textAlign = TextAlign.Center)
            }
        }
    )
}