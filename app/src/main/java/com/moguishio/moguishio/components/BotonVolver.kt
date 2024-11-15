package com.moguishio.moguishio.components

import android.content.Context
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.moguishio.moguishio.R

@Composable
fun BotonVolver(navController: NavHostController, context: Context){
    Button(onClick = {navController.navigateUp()}) {
        EstablecerTexto(
            text = context.getString(R.string.go_back),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.surface
        )
    }
}