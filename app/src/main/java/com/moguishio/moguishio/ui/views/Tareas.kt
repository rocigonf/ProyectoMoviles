package com.moguishio.moguishio.ui.views

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.moguishio.moguishio.R
import com.moguishio.moguishio.ui.components.EstablecerTexto
import com.moguishio.moguishio.ui.components.MakeCheckBox
import com.moguishio.moguishio.ui.theme.AppTypography
import com.moguishio.moguishio.viewmodel.TareasViewmodel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Tareas(context: Context, viewModel: TareasViewmodel) {
    val filmList by viewModel.getAll().collectAsState(initial = emptyList())
    var filmNameInput by remember { mutableStateOf("") }
    var change by remember { mutableStateOf(false) }

    // PARA QUE COMPOSE FUERCE LA REGARGA DE LA PÁGINA, LA VARIABLE TIENE QUE USARSE EN ALGÚN SITIO
    // PONIENDOLO AQUÍ LO USA PERO NO LO MUESTRA >:)
    Text(text = change.toString())

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(95.dp))
        EstablecerTexto(
            text = context.getString(R.string.fav_title),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = AppTypography.displayMedium
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Muestra la lista de películas favoritas
        // DOCS: https://developer.android.com/develop/ui/compose/animation/composables-modifiers?hl=es-419#animatedvisibility
        LazyColumn(
            modifier = Modifier.weight(.7F),
            verticalArrangement = Arrangement.Center
        ) {
            itemsIndexed(items = filmList, key = { _, film -> film.id }) { _, film ->
                // Al decirle que es visible, significa que puede mostrar el contenido...
                // PERO
                // Si pasa a ser no visible, signifca que debe ocultarlo y por tanto se dispara la animación de salida
                var visible by remember { mutableStateOf(true) }
                val density = LocalDensity.current

                AnimatedVisibility(
                    visible = visible,
                    enter = slideInVertically {

                        with(density) { -40.dp.roundToPx() }
                    } + expandVertically(

                        expandFrom = Alignment.Top
                    ) + fadeIn(

                        initialAlpha = 0.3f
                    ),
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    Card(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    ) {
                        Row()
                        {
                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                TextButton(onClick = {
                                    film.vista = !film.vista
                                    viewModel.modificarPelicula(film)
                                    change = !change
                                }) {
                                    var textDecoration: TextDecoration = TextDecoration.None
                                    if (film.vista) {
                                        textDecoration = TextDecoration.LineThrough
                                    }
                                    EstablecerTexto(
                                        text = film.nombre,
                                        textAlign = TextAlign.Left,
                                        style = MaterialTheme.typography.displaySmall,
                                        textDecoration = textDecoration
                                    )
                                }
                            }
                            MakeCheckBox(
                                text = "",
                                isChecked = film.vista,
                                onCheckedChange = {
                                    film.vista = !film.vista
                                    viewModel.modificarPelicula(film)
                                    change = !change
                                }
                            )
                            IconButton(onClick = {
                                visible = false
                                CoroutineScope(Dispatchers.Main).launch {
                                    delay(1000) // 1 segundo
                                    viewModel.borrarPelicula(film)
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = context.getString(R.string.delete)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Campo de texto
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(.3F)
        ) {
            OutlinedTextField(
                value = filmNameInput,
                onValueChange = { filmNameInput = it },
                label = { Text(text = context.getString(R.string.fav_title)) }
            )
            Button(onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.insertarPelicula(filmNameInput)
                }
            }) {
                Text(
                    text = context.getString(R.string.save),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Button(onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.borrarTodasMisPeliculas(filmList)
                }
            }) {
                Text(
                    text = context.getString(R.string.delete_all),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}