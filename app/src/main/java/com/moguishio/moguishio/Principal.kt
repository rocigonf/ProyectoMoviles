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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moguishio.moguishio.components.AlertDialogMenu
import com.moguishio.moguishio.components.CustomCard
import com.moguishio.moguishio.components.EstablecerTexto
import com.moguishio.moguishio.model.Navigation
import com.moguishio.moguishio.ui.theme.AppTypography
import kotlin.system.exitProcess

@Composable
fun Principal(navController: NavHostController, context: Context) {
    val openAlertDialog = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val meme = painterResource(R.drawable.alastor_funny_1)

    // Al pulsar el botón de salir, aparece el dialog preguntando si desea salir o no
    if (openAlertDialog.value){
        AlertDialogMenu(
            dialogText = context.getString(R.string.exit_dialog),
            textAccept = context.getString(R.string.accept_dialog),
            textCancel = context.getString(R.string.cancel_dialog),
            onDismissRequest = {openAlertDialog.value = false},
            onConfirmation = {exitProcess(0)}
        )
    }

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
                onClick = { navController.navigate(Navigation.SobreNosotros.route) },
                text = context.getString(R.string.go_to_about_us)
            )
            CustomCard(
                onClick = { navController.navigate(Navigation.Configuracion.route) },
                text = context.getString(R.string.go_to_config)
            )
            CustomCard( // Botón que cierra la app
                onClick = {openAlertDialog.value = true}, // Al pulsarlo se muestra el AlertDialog
                text = context.getString(R.string.exit_app)
            )
        }
    }
}
