package com.moguishio.moguishio.data

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RadioButtonListExample() {
    val programmingLanguageList = listOf("Java", "Kotlin", "Dart", "Swift")
    var selectedLanguage by remember { mutableStateOf("Kotlin") }
    Column(Modifier.padding(6.dp)) {
        // Recorro la lista para mostrar los checkboxes
        // Cuando el usuario guarde, se busca
        programmingLanguageList.forEach { language ->
            Row(Modifier.padding(6.dp), verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedLanguage == language,
                    onClick = { selectedLanguage = language }
                )
                Text(text = language, color = Color.White, modifier = Modifier.padding(start = 16.dp))
            }
        }
    }
}