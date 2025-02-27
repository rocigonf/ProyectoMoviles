package com.moguishio.moguishio.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ConfigurationDataStore(private val context: Context) {

    // Al igual que el Companion Cube en Portal, sólo hay uno :( (para eso sirve companion)
    companion object {
        // Básicamente como el localStorage de JS o un diccionario...
        // ... El diccionario es "userPreferences", y cada variable es un par clave valor (y "userPreferences" se guarda como en el localStorage)
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userPreferences")

        // Checkboxes
        val SHOW_SUBTITLES = booleanPreferencesKey("show_subtitles")
        val SHOW_NOT_AVAILABLE_FILMS = booleanPreferencesKey("show_not_available_films")
        val SHOW_ONLY_ORIGINAL_FILMS = booleanPreferencesKey("show_only_original_films")

        // Radio button (Alastor, is that you?)
        val LANGUAGE_OPTIONS = intPreferencesKey("language_options")

        // Switches
        val SHOW_PRICES = booleanPreferencesKey("show_price")
        val SHOW_REVIEWS = booleanPreferencesKey("show_reviews")

        // Dropdown Menú
        val DROPDOWN_OPTIONS = intPreferencesKey("dropdown_options")
    }

    // El "?:" aquí sirve para que, si no existe la propiedad, devuelva un valor por defecto
    // El "Flow" sirve para que, cada vez que se actualice la propiedad, vuelva a emitir el valor
    // La T es para decirle que toma cualquier valor
    fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return context.dataStore.data
            .map { preferences ->
                preferences[key] ?: defaultValue
            }
    }

    // Aquí "suspend" es para indicar que es una corrutina
    suspend fun <T> savePreference(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }


    /*val getShowSubtitles: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[SHOW_SUBTITLES] ?: false
        }

    val getShowNotAvailableFilms: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[SHOW_NOT_AVAILABLE_FILMS] ?: false
        }

    val getShowOnlyOriginalFilms: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[SHOW_ONLY_ORIGINAL_FILMS] ?: false
        }

    val getLanguageOptions: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[LANGUAGE_OPTIONS] ?: 1
        }

    suspend fun saveShowSubtitles(showSubtitles: Boolean){
        context.dataStore.edit { preferences ->
            preferences[SHOW_SUBTITLES] = showSubtitles
        }
    }

    suspend fun saveShowNotAvailableFims(showAvailableFims: Boolean){
        context.dataStore.edit { preferences ->
            preferences[SHOW_NOT_AVAILABLE_FILMS] = showAvailableFims
        }
    }*/
}