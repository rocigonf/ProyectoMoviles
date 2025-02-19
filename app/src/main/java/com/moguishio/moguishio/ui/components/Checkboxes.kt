package com.moguishio.moguishio.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MakeCheckBox(
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // "Checkbox" automáticamente sabe que debe cambiar al valor contrario
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
        Spacer(modifier = Modifier.height(10.dp))
        EstablecerTexto(text = text, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.inverseSurface)
    }
}
