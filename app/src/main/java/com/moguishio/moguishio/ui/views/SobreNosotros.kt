package com.moguishio.moguishio.ui.views

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moguishio.moguishio.R
import com.moguishio.moguishio.ui.components.BotonVolver
import com.moguishio.moguishio.ui.components.EstablecerTexto
import com.moguishio.moguishio.ui.theme.AppTypography

//@Preview(showBackground = true)
@Composable
fun SobreNosotros(navController: NavHostController, context: Context) {

    // Imágenes
    val logo = painterResource(R.drawable.logo)
    val alastor = painterResource(R.drawable.alastor)
    val vaggie = painterResource(R.drawable.vaggie)

    // Crea el estado para el scroll
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.inversePrimary)
            .padding(30.dp)
    ){
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(75.dp))

            EstablecerTexto(
                text = context.getString(R.string.aboutUs),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = AppTypography.displayMedium
            )
            Image(
                painter = logo,
                contentDescription = null
            )
            EstablecerTexto(
                text = context.getString(R.string.aboutUsDesc),
                textAlign = TextAlign.Justify,
                color = MaterialTheme.colorScheme.inverseSurface
            )

            // Línea divisoria
            HorizontalDivider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 20.dp)
            )

            EstablecerTexto(
                text = context.getString(R.string.team),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = AppTypography.displaySmall
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row( // El equipo
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column( // Columna Mauricio
                    modifier = Modifier.weight(1f).padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    EstablecerTexto(
                        text = "Mauricio",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        style = AppTypography.headlineLarge
                        )
                    Image(
                        painter = alastor,
                        contentDescription = null,
                        modifier = Modifier
                            .shadow(8.dp, shape = RoundedCornerShape(20.dp))

                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    EstablecerTexto( // Descripción Mauricio
                        text = context.getString(R.string.mauricioDesc),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.inverseSurface,
                    )
                }

                Column( // Columna Rocío
                    modifier = Modifier.weight(1f).padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    EstablecerTexto(
                        text = "Rocío",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        style = AppTypography.headlineLarge
                    )
                    Image(
                        painter = vaggie,
                        contentDescription = null,
                        modifier = Modifier
                            .shadow(8.dp, shape = RoundedCornerShape(20.dp))
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    EstablecerTexto( // Descripción Rocío
                        text = context.getString(R.string.rocioDesc),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.inverseSurface,
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            BotonVolver(navController, context)
        }

        // Esto está comentado xq se ve feo ○|￣|_
        /*
        EstablecerTexto(
            text = context.getString(R.string.license),
            textAlign = TextAlign.Justify,
            style = AppTypography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
        )*/
    }
}
