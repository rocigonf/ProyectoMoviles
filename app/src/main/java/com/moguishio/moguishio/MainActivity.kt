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
import androidx.navigation.compose.rememberNavController
import com.moguishio.moguishio.model.tareas.ContenedorMisPeliculas
import com.moguishio.moguishio.ui.theme.AppTheme
import com.moguishio.moguishio.ui.views.navigationdrawer.NavigationDrawer
import com.moguishio.moguishio.viewmodel.AuthViewModel
import com.moguishio.moguishio.viewmodel.TareasViewmodel
import com.moguishio.moguishio.viewmodel.ViewModelPelicula

class MainActivity : ComponentActivity() {
    lateinit var container: ContenedorMisPeliculas
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        container = ContenedorMisPeliculas(this)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                // Esto es como el router de Angular
                val navController = rememberNavController()
                val context = LocalContext.current
                val filmsViewModel: ViewModelPelicula by viewModels()
                val authViewModel: AuthViewModel by viewModels()
                //val daoMisPeliculas: DaoMisPeliculas
                //val filmRepository: RepositorioMisPeliculas = RepositorioMisPeliculas(daoMisPeliculas = DaoMisPeliculas) //MAL MAL TODO MAL HORRIBLE HORROROSO

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.inversePrimary)
                )
                {
                    //Spacer(modifier = Modifier.height(10.dp))
                    NavigationDrawer(navController, context, filmsViewModel, authViewModel)
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