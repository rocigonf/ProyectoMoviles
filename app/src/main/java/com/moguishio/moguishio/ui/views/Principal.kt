package com.moguishio.moguishio.ui.views

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moguishio.moguishio.R
import com.moguishio.moguishio.model.Navigation
import com.moguishio.moguishio.ui.components.AlertDialogMenu
import com.moguishio.moguishio.ui.components.CustomCard
import com.moguishio.moguishio.ui.components.EstablecerTexto
import com.moguishio.moguishio.ui.theme.AppTypography
import com.moguishio.moguishio.viewmodel.AuthState
import com.moguishio.moguishio.viewmodel.AuthViewModel
import kotlin.system.exitProcess

@Composable
fun Principal(navController: NavHostController, context: Context,
authViewModel: AuthViewModel
) {
    val openAlertDialog = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val meme = painterResource(R.drawable.foto)

    val authState = authViewModel.authState.observeAsState()
    val email = remember { mutableStateOf(authViewModel.username) }
    val isLogged = remember { mutableStateOf(false) }

    var emailValorant = email.value
    // De nuevo, en el vídeo no es así, pero meh
    LaunchedEffect(authState.value) {
        when(authState.value)
        {
            is AuthState.Authenticated -> isLogged.value = true
            is AuthState.Unauthenticated -> isLogged.value = false
            else -> Unit
        }
    }

    LaunchedEffect(email.value) {
        emailValorant = email.value
    }

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
                //.fillMaxSize()
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
                contentDescription = null,
                modifier = Modifier.clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.height(20.dp))

            if(emailValorant != "")
            {
                EstablecerTexto(
                    text = context.getString(R.string.hello) + " " + email.value,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Esto es una función custom porque no soy el dev de Yandere :( (la de copiar y pegar código repetido no va conmigo)
            CustomCard(
                onClick = { navController.navigate(Navigation.Peliculas.route) },
                text = context.getString(R.string.go_to_films)
            )
            CustomCard(
                onClick = { navController.navigate(Navigation.Tareas.route) },
                text = context.getString(R.string.go_to_films)
            )
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

            // Creo que se podría usar el authState directamente
            if(isLogged.value)
            {
                CustomCard(
                    onClick = { authViewModel.signOut() },
                    text = context.getString(R.string.log_out)
                )
            }
            else
            {
                CustomCard(
                    onClick = { navController.navigate(Navigation.InicioSesion.route) },
                    text = context.getString(R.string.go_to_login)
                )
            }

            CustomCard( // Botón que cierra la app
                onClick = {openAlertDialog.value = true}, // Al pulsarlo se muestra el AlertDialog
                text = context.getString(R.string.exit_app)
            )
        }
    }
}
