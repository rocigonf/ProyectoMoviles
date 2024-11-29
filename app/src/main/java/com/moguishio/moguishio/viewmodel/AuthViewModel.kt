package com.moguishio.moguishio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.moguishio.moguishio.R.string.error
import com.moguishio.moguishio.R.string.no_empty_fields

class AuthViewModel : ViewModel()
{
    private val auth : FirebaseAuth = FirebaseAuth.getInstance() // Para acceder a las funciones de Firebase

    // La primera variable puede ser modificada (por el Mutable) pero sólo dentro de esta clase...
    // ...La segunda únicamente es de lectura para que ninguna vista tocapelotas pueda modificar el valor
    private val _authState = MutableLiveData<AuthState>()
    val authState : LiveData<AuthState> = _authState


    // El ngOnInit de Angular básicamente
    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        // Ojalá en ASP fuese tan fácil xD
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated
        }
    }

    // El vídeo no lo hace así, pero me da tock :)
    fun loginOrSignUp(email: String, password: String, isLogin: Boolean)
    {

        if(email.isEmpty() || password.isEmpty())
        {
            _authState.value = AuthState.Error(no_empty_fields.toString())
            return
        }

        _authState.value = AuthState.Loading // Para decirle que está procesando la petición

        val task = if (isLogin) {
            auth.createUserWithEmailAndPassword(email, password)
        } else {
            auth.signInWithEmailAndPassword(email, password)
        }

        // El "taskResult" es como el "result" de Angular
        task.addOnCompleteListener { taskResult ->
            if(taskResult.isSuccessful)
            {
                _authState.value = AuthState.Authenticated
            }
            else
            {
                _authState.value = AuthState.Error(task.exception?.message?: error.toString())
            }
        }
    }

    fun signOut()
    {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }
}

// Para definir los posibles estados que puede tener el usuario (autenticado, sin autenticar, etc.)
sealed class AuthState
{
    // En el vídeo sale sin "data", pero me da tock tener advertencias😭
    data object Authenticated : AuthState()
    data object Unauthenticated : AuthState()
    data object Loading : AuthState()
    data class Error(val message: String) : AuthState() // Tiene que ser una clase para que pueda mostrar un mensaje de error
}