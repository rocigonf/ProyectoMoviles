package com.moguishio.moguishio.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable

fun CustomButton(onClick : () -> Unit, text: String, @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.width(300.dp))
{
    Button(onClick = onClick, modifier = modifier)
    {
        EstablecerTexto(
            text = text,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.surface
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}