package com.moguishio.moguishio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.moguishio.moguishio.data.ConfigurationDataStore
import com.moguishio.moguishio.data.MakeCheckBox
import com.moguishio.moguishio.data.RadioButtonGroup
import com.moguishio.moguishio.ui.theme.AppTypography
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
    val showNotAvailableFilms = configDataStore.getPreference(ConfigurationDataStore.SHOW_NOT_AVAILABLE_FILMS, false).collectAsState(initial = false)
    val showOriginalFilms = configDataStore.getPreference(ConfigurationDataStore.SHOW_ONLY_ORIGINAL_FILMS, false).collectAsState(initial = false)
    val selectedLanguageIndex = configDataStore.getPreference(ConfigurationDataStore.LANGUAGE_OPTIONS, 1).collectAsState(initial = 1)

    val isSubtitlesChecked = remember { mutableStateOf(showSubtitles.value) }
    val isAvailableFilms = remember { mutableStateOf(showNotAvailableFilms.value) }
    val isOriginalOnly = remember { mutableStateOf(showOriginalFilms.value) }
    val selectedRadioButtonIndex = remember { mutableIntStateOf(selectedLanguageIndex.value) }

    // En teoría esto es necesario para monitorizar los cambios de la variable (ya que no sirve "remember" y "mutableStateOf")
    LaunchedEffect(showSubtitles.value, showNotAvailableFilms.value, selectedLanguageIndex.value, showOriginalFilms.value) {
        isSubtitlesChecked.value = showSubtitles.value
        isAvailableFilms.value = showNotAvailableFilms.value
        isOriginalOnly.value = showOriginalFilms.value
        selectedRadioButtonIndex.intValue = selectedLanguageIndex.value
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
            EstablecerTexto(
                text = context.getString(R.string.configuration),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = AppTypography.displayLarge
            )
            Spacer(modifier = Modifier.height(50.dp))

            MakeCheckBox(
                text = context.getString(R.string.show_if_subtitles),
                isChecked = isSubtitlesChecked.value,
                onCheckedChange = { isSubtitlesChecked.value = it }
            )

            MakeCheckBox(
                text = context.getString(R.string.show_not_available),
                isChecked = isAvailableFilms.value,
                onCheckedChange = { isAvailableFilms.value = it }
            )

            MakeCheckBox(
                text = context.getString(R.string.show_originals),
                isChecked = isOriginalOnly.value,
                onCheckedChange = { isOriginalOnly.value = it }
            )

            Spacer(Modifier.height(16.dp))

            val radioButtons = listOf(context.getString(R.string.films_with_translation), context.getString(R.string.language_doesnt_mind))
            RadioButtonGroup(
                options = radioButtons,
                selectedLanguageIndex = selectedRadioButtonIndex.intValue,
                onSelectionChange = { selectedRadioButtonIndex.intValue = it }
            )

            Spacer(Modifier.height(16.dp))

            // Botón para guardar el nuevo valor
            Button(
                onClick = {
                    coroutineScope.launch {
                        configDataStore.savePreference(ConfigurationDataStore.SHOW_SUBTITLES, isSubtitlesChecked.value)
                        configDataStore.savePreference(ConfigurationDataStore.SHOW_NOT_AVAILABLE_FILMS, isAvailableFilms.value)
                        configDataStore.savePreference(ConfigurationDataStore.SHOW_ONLY_ORIGINAL_FILMS, isOriginalOnly.value)
                        configDataStore.savePreference(ConfigurationDataStore.LANGUAGE_OPTIONS, selectedRadioButtonIndex.intValue)
                    }
                }
            ){
                Text(
                    text = context.getString(R.string.save_button)
                )
            }

        }
    }
}
