package com.moguishio.moguishio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.moguishio.moguishio.ui.theme.AppTypography

@Composable
fun EstablecerTexto(modifier: Modifier = Modifier, text: String, textAlign: TextAlign, style: TextStyle = MaterialTheme.typography.titleLarge, fontWeight: FontWeight = FontWeight.Normal, color: Color = MaterialTheme.colorScheme.primary)
{
    Text(
        text = text,
        softWrap = true,
        textAlign = textAlign,
        fontWeight = fontWeight,
        color = color,
        style = style,
        modifier = modifier.fillMaxWidth() // El "fillMaxWidth" está porque es bobolón y si no lo tiene, no centra el texto
    )
}

@Composable
fun MainPage() {
    val context = LocalContext.current
    val image = painterResource(R.drawable.scale)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inversePrimary)
            .padding(30.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            EstablecerTexto(
                text = context.getString(R.string.help),
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
                text = context.getString(R.string.description),
                textAlign = TextAlign.Justify,
                color = MaterialTheme.colorScheme.inverseSurface
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    // Aquí cambiaríamos de vista. ¿Cómo? ª
                },
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = context.getString(R.string.changeview))
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
