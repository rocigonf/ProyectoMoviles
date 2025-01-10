package com.moguishio.moguishio.ui.views

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moguishio.moguishio.R
import com.moguishio.moguishio.ui.components.EstablecerTexto
import com.moguishio.moguishio.ui.theme.AppTypography
import com.moguishio.moguishio.viewmodel.TareasViewmodel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Tareas(context: Context) {
    val viewModel: TareasViewmodel = viewModel(factory = TareasViewmodel.Factory)
    val filmList by viewModel.getAll().collectAsState(initial = emptyList())
    var filmNameInput by remember { mutableStateOf("") }

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
        LazyColumn(
            modifier = Modifier.weight(.7F),
            verticalArrangement = Arrangement.Center
        ) {
            itemsIndexed(items = filmList) { _, film ->
                Card(
                    modifier = Modifier
                        .width(200.dp)
                        .height(80.dp)
                        .padding(vertical = 8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        TextButton (onClick = {
                            film.vista = !film.vista
                            viewModel.modificarPelicula(film)
                        }){
                            var textDecoration : TextDecoration = TextDecoration.None
                            if(film.vista)
                            {
                                textDecoration = TextDecoration.LineThrough
                            }
                            EstablecerTexto(
                                text = film.nombre,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.displaySmall,
                                textDecoration = textDecoration
                            )
                        }
                    }
                }
                Button(onClick = {
                    film.vista = !film.vista
                    viewModel.modificarPelicula(film)
                }) {
                    Text(context.getString(R.string.modify))
                }
                Button(onClick = {
                    viewModel.borrarPelicula(film)
                }) {
                    Text(context.getString(R.string.delete))
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
                label = { Text(text = context.getString(R.string.fav_title))}
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