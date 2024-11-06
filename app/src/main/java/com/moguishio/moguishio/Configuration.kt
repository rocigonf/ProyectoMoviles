package com.moguishio.moguishio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.moguishio.moguishio.data.ConfigurationDataStore
import com.moguishio.moguishio.data.MakeCheckBox
import com.moguishio.moguishio.data.RadioButtonListExample
import kotlinx.coroutines.launch

// TODO: No tengo muy claro lo del state hoisting, preguntar a Juanma

@Composable
fun ConfigPage() {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Para cargar y guardar los valores, respectivamente
    val configDataStore = ConfigurationDataStore(context)
    val coroutineScope = rememberCoroutineScope()

    // Se especifican dos valores como iniciales / por defecto porque es igual a como se hace en Angular...
    // ... Mientras la función "getPreference" se ubica, le pone un valor
    val showSubtitles = configDataStore.getPreference(ConfigurationDataStore.SHOW_SUBTITLES, false).collectAsState(initial = false)

    val isMondongoChecked = remember { mutableStateOf(false) }

    // En teoría esto es necesario para monitorizar los cambios de la variable (ya que no sirve "remember" y "mutableStateOf")
    LaunchedEffect(showSubtitles.value) {
        isMondongoChecked.value = showSubtitles.value
    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.inversePrimary)
            .padding(30.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){

            MakeCheckBox(
                text = "MONDONGO",
                isChecked = isMondongoChecked.value,
                onCheckedChange = { isMondongoChecked.value = it }
            )

            // Botón para guardar el nuevo valor
            Button(
                onClick = {
                    coroutineScope.launch {
                        configDataStore.savePreference(ConfigurationDataStore.SHOW_SUBTITLES, isMondongoChecked.value)
                    }
                }
            ){
                Text(
                    text = "Guarda, mamahuevo"
                )
            }
            RadioButtonListExample()
        }
    }
}
