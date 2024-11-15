package com.moguishio.moguishio.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun RadioButtonGroup(
    options: List<String>,
    selectedLanguageIndex: Int,
    onSelectionChange: (Int) -> Unit
) {
    Column(Modifier.padding(6.dp)) {
        // Recorro la lista para mostrar los radio buttons con "forEachIndexed" (que le asigna un índice automáticamente)
        options.forEachIndexed { index, language ->
            Row(Modifier.padding(6.dp), verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedLanguageIndex == index, // Si su posición coindice con la seleccionada, es la que cargo
                    onClick = { onSelectionChange(index) }
                )
                EstablecerTexto(text = language, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.inverseSurface)
            }
        }
    }
}