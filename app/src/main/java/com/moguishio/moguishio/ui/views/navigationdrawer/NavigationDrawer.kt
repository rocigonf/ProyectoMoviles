package com.moguishio.moguishio.ui.views.navigationdrawer

//import com.moguishio.moguishio.model.tareas.RepositorioMisPeliculas
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moguishio.moguishio.R
import com.moguishio.moguishio.model.Navigation
import com.moguishio.moguishio.ui.views.ConfigPage
import com.moguishio.moguishio.ui.views.Films
import com.moguishio.moguishio.ui.views.MainPage
import com.moguishio.moguishio.ui.views.Principal
import com.moguishio.moguishio.ui.views.SobreNosotros
import com.moguishio.moguishio.ui.views.Tareas
import com.moguishio.moguishio.ui.views.auth.InicioSesion
import com.moguishio.moguishio.ui.views.auth.Registro
import com.moguishio.moguishio.viewmodel.AuthViewModel
import com.moguishio.moguishio.viewmodel.ViewModelPelicula
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter") // Necesario porque da un error (ni idea de por qué)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    navController: NavHostController,
    context: Context,
    filmsViewModel: ViewModelPelicula,
    authViewModel: AuthViewModel
) {
    val items = listOf(
        // TODO: Cambiar iconos
        NavigationItems(
            title = context.getString(R.string.main),
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = "Principal"
        ),
        NavigationItems(
            title = "Tareas",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = "Tareas"
        ),
        NavigationItems(
            title = context.getString(R.string.overview),
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info,
            route = "AcercaDe"
        ),
        NavigationItems(
            title = context.getString(R.string.aboutUs),
            selectedIcon = Icons.Filled.Edit,
            unselectedIcon = Icons.Outlined.Edit,
            route = "SobreNosotros"
        ),
        NavigationItems(
            title = context.getString(R.string.configuration),
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            route = "Configuration"
        ),
        NavigationItems(
            title = context.getString(R.string.films),
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            route = "Films"
        ),
        // Falta chequear si el usuario tiene sesión
    )

    // La opción seleccionada
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = { Text(text = item.title) },
                        selected = index == selectedItemIndex,
                        onClick = {
                            navController.navigate(item.route)

                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        // Padding entre los elementos
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }

            }
        },
        gesturesEnabled = true
    ) {
        NavHost(navController = navController, startDestination = Navigation.Principal.route) {
            composable(Navigation.Principal.route) {
                Principal(
                    navController,
                    context,
                    authViewModel
                )
            }
            composable(Navigation.AcercaDe.route) { MainPage(navController, context) }
            composable(Navigation.SobreNosotros.route) { SobreNosotros(navController, context) }
            composable(Navigation.Configuracion.route) { ConfigPage(navController, context) }
            composable(Navigation.Peliculas.route) { Films(navController, context, filmsViewModel) }
            composable(Navigation.Tareas.route) { Tareas() }
            composable(Navigation.InicioSesion.route) {
                InicioSesion(
                    navController,
                    context,
                    authViewModel
                )
            }
            composable(Navigation.Registro.route) {
                Registro(
                    navController,
                    context,
                    authViewModel
                )
            }
        }
        TopAppBar(
            title = {
                Text(text = context.getString(R.string.app_name))
            },
            navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu"
                    )
                }
            }
        )

    }

}