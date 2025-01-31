package com.moguishio.moguishio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.moguishio.moguishio.ui.theme.AppTheme
import com.moguishio.moguishio.ui.views.navigationdrawer.NavigationDrawer
//import com.moguishio.moguishio.viewmodel.AuthViewModel
import com.moguishio.moguishio.viewmodel.TareasViewmodel
import com.moguishio.moguishio.viewmodel.ViewModelPelicula
import com.moguishio.moguishio.viewmodel.authentication.ViewModelAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                // Esto es como el router de Angular
                val navController = rememberNavController()
                val context = LocalContext.current
                val filmsViewModel: ViewModelPelicula by viewModels()
                //val authViewModel: AuthViewModel by viewModels()
                val viewmodelAuthDefinitivo : ViewModelAuth = viewModel(factory = ViewModelAuth.Factory)
                val tareasViewmodel: TareasViewmodel = viewModel(factory = TareasViewmodel.Factory)
                //val daoMisPeliculas: DaoMisPeliculas
                //val filmRepository: RepositorioMisPeliculas = RepositorioMisPeliculas(daoMisPeliculas = DaoMisPeliculas) //MAL MAL TODO MAL HORRIBLE HORROROSO

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.inversePrimary)
                )
                {
                    NavigationDrawer(navController, context, filmsViewModel, tareasViewmodel, viewmodelAuthDefinitivo)
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