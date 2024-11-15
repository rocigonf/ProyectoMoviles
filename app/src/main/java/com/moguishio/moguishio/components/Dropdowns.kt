package com.moguishio.moguishio.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(
    text: String,
    options: List<String>,
    selectedOptionIndex: Int,
    onOptionChange: (Int) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    val hellIsForever = isSystemInDarkTheme()
    val dark by remember { mutableStateOf(hellIsForever) }

    val darkScheme = darkColorScheme(
        surfaceVariant = Color(0xFF182C64),
        surface = Color(0xFF182C64)
    )

    MaterialTheme(colorScheme = if (dark) darkScheme else lightColorScheme()) {
        Row(
            modifier = Modifier.padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .width(200.dp),
                expanded = isExpanded,

                onExpandedChange = { isExpanded = !isExpanded }
            ) {
                TextField(
                    modifier = Modifier.menuAnchor(),
                    // Obtiene el valor de options en el índice seleccionado. Si recibe un índice inválido, devuelve el primero por defecto.
                    value = options.getOrElse(selectedOptionIndex) { options[0] },
                    onValueChange = {},
                    readOnly = true,
                    // Muestra la flechita del menú en la derecha y cambia según el estado del menú (apunta arriba o abajo)
                    trailingIcon = { TrailingIcon(expanded = isExpanded) },
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.inverseSurface,
                        fontWeight = FontWeight.Bold
                    ),
                    label = {
                        Text(
                            text = text,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.inverseSurface
                        )
                    },
                )

                // Opciones del menú
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }) {
                    options.forEachIndexed { index, text ->
                        DropdownMenuItem(
                            text = {
                                EstablecerTexto(
                                    text = text,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.inverseSurface
                                )
                            },
                            onClick = {
                                onOptionChange(index)
                                isExpanded = false // El menú se pliega al seleccionar una opción
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )


                    }
                }
            }
        }
    }
}

//dios santo que asco de dropdown feo
// ya no 😎 BUM