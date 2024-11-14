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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moguishio.moguishio.ui.theme.AppTypography

@Composable
fun MainPage(navController: NavHostController, context: Context) {
    val image = painterResource(R.drawable.foto)

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.inversePrimary)
            .padding(30.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            EstablecerTexto(
                text = context.getString(R.string.overview),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = AppTypography.displayLarge
            )
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier.clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.height(20.dp))
            EstablecerTexto(
                text = context.getString(R.string.overviewDesc),
                textAlign = TextAlign.Justify,
                color = MaterialTheme.colorScheme.inverseSurface
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {navController.navigateUp()}) {
                EstablecerTexto(
                    text = context.getString(R.string.go_back),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }

        // El texto para las licencias (lo pongo abajo rollo footer)
        EstablecerTexto(
            text = context.getString(R.string.license),
            textAlign = TextAlign.Justify,
            style = AppTypography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
        )
    }
}
