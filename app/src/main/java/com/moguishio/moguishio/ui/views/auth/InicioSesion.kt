package com.moguishio.moguishio.ui.views.auth

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moguishio.moguishio.R
import com.moguishio.moguishio.ui.components.BotonVolver
import com.moguishio.moguishio.ui.components.CustomButton
import com.moguishio.moguishio.ui.components.EstablecerTexto
import com.moguishio.moguishio.ui.components.PasswordInput
import com.moguishio.moguishio.viewmodel.AuthState
import com.moguishio.moguishio.viewmodel.AuthViewModel

@Composable
fun InicioSesion(navController: NavHostController, context: Context, authViewModel: AuthViewModel)
{
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState = authViewModel.authState.observeAsState()

    // En función de lo que pase, te manda a un sitio u a otro
    LaunchedEffect(authState.value) {
        when(authState.value)
        {
            is AuthState.Authenticated -> navController.navigate("Principal")
            is AuthState.Error -> Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        EstablecerTexto(text = context.getString(R.string.login_page), style = MaterialTheme.typography.displayLarge, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = context.getString(R.string.email)) }
        )
        Spacer(modifier = Modifier.height(8.dp))

        PasswordInput(
            value = password,
            onValueChange = { password = it },
            text = context.getString(R.string.password)
        )
        Spacer(modifier = Modifier.height(32.dp))

        CustomButton(onClick = {
            authViewModel.loginOrSignUp(email.trim(), password, true)
        }, text = context.getString(R.string.login_page))
        //CustomButton({}, text = context.getString(R.string.new_account))

        TextButton(onClick = { navController.navigate("Registro")}) {
            EstablecerTexto(text = context.getString(R.string.new_account), textAlign = TextAlign.Center, fontWeight = FontWeight.Thin)
        }

        Spacer(modifier = Modifier.height(64.dp))
        BotonVolver(navController, context)
    }
}