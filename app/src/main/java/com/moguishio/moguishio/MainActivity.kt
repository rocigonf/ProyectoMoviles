package com.moguishio.moguishio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moguishio.moguishio.model.Navigation
import com.moguishio.moguishio.ui.theme.AppTheme
import com.moguishio.moguishio.ui.views.ConfigPage
import com.moguishio.moguishio.ui.views.Films
import com.moguishio.moguishio.ui.views.MainPage
import com.moguishio.moguishio.ui.views.Principal
import com.moguishio.moguishio.ui.views.SobreNosotros
import com.moguishio.moguishio.ui.views.auth.InicioSesion
import com.moguishio.moguishio.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                // Esto es como el router de Angular
                val navController = rememberNavController()
                val context = LocalContext.current
                val authViewModel : AuthViewModel by viewModels()

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.inversePrimary)
                )
                {
                    Spacer(modifier = Modifier.height(10.dp))

                    // Aquí se definen todas las rutas...
                    // ... Tantas referencias a Angular me van a volver INSANE, pero es como el "app.routes.ts"
                    NavHost(navController = navController, startDestination = Navigation.Principal.route) {
                        composable(Navigation.Principal.route) { Principal(navController, context, authViewModel) }
                        composable(Navigation.AcercaDe.route) { MainPage(navController, context) }
                        composable(Navigation.SobreNosotros.route) { SobreNosotros(navController, context) }
                        composable(Navigation.Configuracion.route) { ConfigPage(navController, context) }
                        composable(Navigation.Films.route) { Films(navController, context) }
                        composable(Navigation.InicioSesion.route) { InicioSesion(navController, context, authViewModel) }
                    }
                }
            }
        }
    }
}

// COMENTO LOS PREVIEW POR AHORA PORQUE JUANMA NO LOS PIDE (y pueden ser un grano en el culo)
// joe macho :(

/*
// Preview Acerca De
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AcercaDePreview(){
    AppTheme {
        MainPage()
    }
}

// Preview Sobre Nosotros
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SobreNosotrosPreview(){
    AppTheme {
        SobreNosotros()
    }
}
*/

//hey sorry about you just got in my way i promise honey i can feel your pain maybe i enjoy it just a little bit does that make me INSANE PAPA PA PAPA PA PAPA PA PAPA