package com.moguishio.moguishio

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moguishio.moguishio.navigation.Navigation
import com.moguishio.moguishio.ui.theme.AppTypography

@Composable
fun Principal(navController: NavHostController, context: Context) {
    val scrollState = rememberScrollState()
    val meme = painterResource(R.drawable.alastor_funny_1)
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.inversePrimary)
            .padding(30.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EstablecerTexto(
                text = context.getString(R.string.app_name),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = AppTypography.displayLarge
            )
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = meme,
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Esto es una función custom porque no soy el dev de Yandere :( (la de copiar y pegar código repetido no va conmigo)
            CustomCard(
                onClick = { navController.navigate(Navigation.AcercaDe.route) },
                text = context.getString(R.string.go_to_overview)
            )
            CustomCard(
                onClick = { navController.navigate(Navigation.Configuracion.route) },
                text = context.getString(R.string.go_to_config)
            )
        }
    }
}
