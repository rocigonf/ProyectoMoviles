package com.moguishio.moguishio.ui.views.auth

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moguishio.moguishio.R
import com.moguishio.moguishio.ui.components.BotonVolver
import com.moguishio.moguishio.ui.components.CustomButton
import com.moguishio.moguishio.ui.components.EstablecerTexto
import com.moguishio.moguishio.viewmodel.AuthViewModel

@Composable
fun InicioSesion(navController: NavHostController, context: Context, authViewModel: AuthViewModel)
{
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = context.getString(R.string.password)) }
        )
        Spacer(modifier = Modifier.height(32.dp))

        CustomButton({}, text = context.getString(R.string.login_page))
        //CustomButton({}, text = context.getString(R.string.new_account))

        // Texto botón Texto botón Bottom Text
        // El onClick ahora mismo peta bastísimo porque no existe la ruta
        TextButton(onClick = { navController.navigate("Registro")}) {
            Text(text = context.getString(R.string.new_account))
        }

        Spacer(modifier = Modifier.height(64.dp))
        BotonVolver(navController, context)
    }
}