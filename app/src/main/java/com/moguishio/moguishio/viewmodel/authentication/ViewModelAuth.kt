package com.moguishio.moguishio.viewmodel.authentication

import android.content.Context
import android.util.Patterns
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moguishio.moguishio.R.string.invalid_email
import com.moguishio.moguishio.R.string.no_empty_fields
import com.moguishio.moguishio.model.authentication.AuthClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// ESTA CLASE ES UNA MEZCLA ENTRE "AuthViewModel" Y "ConfigurationDataStore"
// Da una advertencia rara 🐌🐌
class ViewModelAuth(private val context: Context, private val auth : AuthClient) : ViewModel() {
    private val _authState = MutableLiveData<AuthState>()
    val authState : LiveData<AuthState> = _authState

    var email = ""
    var accessToken = ""
    var refreshToken = ""

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("dataStoreAuth")

        val EMAIL = stringPreferencesKey("email")
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    fun <T> getInfo(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return context.dataStore.data
            .map { preferences ->
                preferences[key] ?: defaultValue
            }
    }

    suspend fun <T> saveInfo(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    // Quizás haya que cambiar la forma en la que se obtienen los datos
    init {
        email = getInfo(EMAIL, "").toString()
        accessToken = getInfo(ACCESS_TOKEN, "").toString()
        refreshToken = getInfo(REFRESH_TOKEN, "").toString()
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // A PARTIR DE AQUÍ FALTA LLAMAR A LOS REPOSITORIOS Y LOS SERVICIOS PARA HACER LA PETICIÓN Y PROCESAR LA RESPUESTA
    suspend fun getUserDataAndSave(email: String)
    {
        saveInfo(EMAIL, email)
    }

    suspend fun refreshAndSaveToken()
    {
    }

    fun login(email: String, password: String)
    {

        if(email.isEmpty() || password.isEmpty())
        {
            _authState.value = AuthState.Error(no_empty_fields.toString())
            return
        }

        if (!isValidEmail(email)) {
            _authState.value = AuthState.Error(invalid_email.toString())
            return
        }

        _authState.value = AuthState.Loading
    }

    fun signup(email: String, password: String)
    {
        if(email.isEmpty() || password.isEmpty())
        {
            _authState.value = AuthState.Error(no_empty_fields.toString())
            return
        }

        if (!isValidEmail(email)) {
            _authState.value = AuthState.Error(invalid_email.toString())
            return
        }

        _authState.value = AuthState.Loading
    }

    fun signout()
    {
        _authState.value = AuthState.Unauthenticated

        email = ""
        accessToken = ""
        refreshToken = ""
    }
}

// Para definir los posibles estados que puede tener el usuario (autenticado, sin autenticar, etc.)
sealed class AuthState
{
    data object Authenticated : AuthState()
    data object Unauthenticated : AuthState()
    data object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}