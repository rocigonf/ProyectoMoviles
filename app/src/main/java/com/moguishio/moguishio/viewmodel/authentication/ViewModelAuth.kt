package com.moguishio.moguishio.viewmodel.authentication

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.moguishio.moguishio.AplicacionTareas
import com.moguishio.moguishio.model.authentication.AuthRepository
import com.moguishio.moguishio.model.authentication.AuthRequest
import com.moguishio.moguishio.model.authentication.TokenRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

// ESTA CLASE ES UNA MEZCLA ENTRE "AuthViewModel" Y "ConfigurationDataStore"
// Da una advertencia rara 🐌🐌
@Suppress("SameParameterValue")
@SuppressLint("StaticFieldLeak")
class ViewModelAuth(private val context: Context) : ViewModel() {
    private val auth : AuthRepository = AuthRepository()

    private val _authState = MutableLiveData<AuthState>()
    val authState : LiveData<AuthState> = _authState

    private val _accessToken = MutableLiveData<String>()
    var accessToken : LiveData<String> = _accessToken

    // HABRÁ QUE CASTEAR EL ID A INT (guardarlo en DataStore como Int falla a veces)
    private val _id = MutableLiveData<String>()
    var id : LiveData<String> = _id

    private val _refreshToken = MutableLiveData<String>()
    var refreshToken : LiveData<String> = _refreshToken

    private val _email = MutableLiveData<String>()
    var email : LiveData<String> = _email

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AplicacionTareas)
                ViewModelAuth(application.container.context)
            }
        }

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("dataStoreAuth")

        val EMAIL = stringPreferencesKey("email")
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val ID = stringPreferencesKey("ID_USER")
    }

    fun<T> getInfo(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
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

    init {
        getData()
    }

    // Hago 3 corrutinas porque falla con una sola (solo se ejecuta la primera cosa)
    fun getData()
    {
        CoroutineScope(Dispatchers.Main).launch {
            _refreshToken.value = getInfo(REFRESH_TOKEN, "").collect { _refreshToken.value = it }.toString()
        }
        CoroutineScope(Dispatchers.Main).launch {
            _email.value = getInfo(EMAIL, "").collect { _email.value = it }.toString()
        }
        CoroutineScope(Dispatchers.Main).launch {
            _accessToken.value = getInfo(ACCESS_TOKEN, "").collect { _accessToken.value = it }.toString()
        }
        CoroutineScope(Dispatchers.Main).launch {
            _id.value = getInfo(ID, "").collect { _id.value = it }.toString()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // A PARTIR DE AQUÍ FALTA LLAMAR A LOS REPOSITORIOS Y LOS SERVICIOS PARA HACER LA PETICIÓN Y PROCESAR LA RESPUESTA
    private suspend fun getUserDataAndSave(emailInput: String)
    {
        val token = "Bearer ${accessToken.value}"
        val userData = auth.getUserData(token, emailInput)
        //Log.e("EEEE", "MORTADELA")
        if(userData != null) {
            Log.e("EMAIL", userData.email)
            _id.value = userData.id.toString()
            _email.value = userData.email
            saveInfo(EMAIL, userData.email)
            saveInfo(ID, userData.id.toString())
        }
    }

    suspend fun refreshAndSaveToken()
    {
        Log.e("ACCESS", accessToken.value.toString())
        //val token = "Bearer ${accessToken.value.toString()}"
        Log.e("REFRESH", refreshToken.value.toString())
        val tokenResponse = auth.refreshToken(TokenRequest(refreshToken.value.toString()))
        if(tokenResponse != null)
        {
            Log.e("RESULT", "Se ha refrescado")
            _accessToken.value = tokenResponse.accessToken
            saveInfo(ACCESS_TOKEN, tokenResponse.accessToken)
            saveInfo(REFRESH_TOKEN, refreshToken.value.toString())
            _authState.value = AuthState.Authenticated
        }
        else
        {
            _authState.value = AuthState.Unauthenticated
            signout()
        }
    }

    suspend fun login(email: String, password: String)
    {
        //_email.value = email

        if(email.isEmpty() || password.isEmpty())
        {
            _authState.value = AuthState.Error("Sin campos nulos")
            return
        }

        if(password.length < 6)
        {
            _authState.value = AuthState.Error("Password")
            return
        }

        if (!isValidEmail(email)) {
            _authState.value = AuthState.Error("Mail incorrecto")
            return
        }

        _authState.value = AuthState.Loading

        val loginResponse = auth.login(AuthRequest(email, password))
        if(loginResponse == null)
        {
            _authState.value = AuthState.Error("Datos incorrectos")
            _email.value = ""
        }
        else
        {
            _accessToken.value = loginResponse.accessToken
            _refreshToken.value = loginResponse.refreshToken

            saveInfo(REFRESH_TOKEN, loginResponse.refreshToken)
            saveInfo(ACCESS_TOKEN, loginResponse.accessToken)

            _email.value = email
            saveInfo(EMAIL, email)

            getUserDataAndSave(email)

            _authState.value = AuthState.Authenticated
        }
    }

    suspend fun signup(emailInput: String, password: String)
    {
        if(emailInput.isEmpty() || password.isEmpty())
        {
            _authState.value = AuthState.Error("Sin campos nulos")
            return
        }

        if(password.length < 6)
        {
            _authState.value = AuthState.Error("Password")
            return
        }

        if (!isValidEmail(emailInput)) {
            _authState.value = AuthState.Error("Mail incorrecto")
            return
        }

        _authState.value = AuthState.Loading

        val signUpResponse = auth.signup(AuthRequest(emailInput, password))
        if(signUpResponse == null)
        {
            _authState.value = AuthState.Error("No se ha podido registrar")
        }
        else
        {
            _email.value = signUpResponse.email
            _id.value = signUpResponse.id.toString()
            saveInfo(EMAIL, signUpResponse.email)
            saveInfo(ID, signUpResponse.id.toString())
            _authState.value = AuthState.Authenticated
        }
    }

    suspend fun signout()
    {
        _authState.value = AuthState.Unauthenticated

        _email.value = ""
        _accessToken.value = ""
        _refreshToken.value = ""
        _id.value = "0"

        saveInfo(EMAIL, "")
        saveInfo(ACCESS_TOKEN, "")
        saveInfo(REFRESH_TOKEN, "")
        saveInfo(ID, "0")
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