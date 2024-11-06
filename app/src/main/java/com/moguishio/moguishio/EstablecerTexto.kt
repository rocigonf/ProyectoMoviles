package com.moguishio.moguishio

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun EstablecerTexto(modifier: Modifier = Modifier, text: String, textAlign: TextAlign, style: TextStyle = MaterialTheme.typography.titleLarge, fontWeight: FontWeight = FontWeight.Normal, color: Color = MaterialTheme.colorScheme.primary)
{
    Text(
        text = text,
        softWrap = true,
        textAlign = textAlign,
        fontWeight = fontWeight,
        color = color,
        style = style,
        modifier = modifier.fillMaxWidth() // El "fillMaxWidth" está porque es bobolón y si no lo tiene, no centra el texto
    )
}