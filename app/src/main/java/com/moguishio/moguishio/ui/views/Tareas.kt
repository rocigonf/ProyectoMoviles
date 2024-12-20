package com.moguishio.moguishio.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moguishio.moguishio.model.tareas.MiPelicula
import com.moguishio.moguishio.model.tareas.RepositorioMisPeliculas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TareasView(
    modifier: Modifier = Modifier,
    repository: RepositorioMisPeliculas)
{

    val filmList by repository.getAll().collectAsState(initial = emptyList())
    var filmNameInput by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Muestra la lista de películas
        LazyColumn(
            modifier = Modifier.weight(.7F),
            verticalArrangement = Arrangement.Center
        ) {
            itemsIndexed(items = filmList) { index, film ->
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
                        Text(text = film.nombre, style = MaterialTheme.typography.displaySmall)
                    }
                }
            }
        }

        // Input field and buttons
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(.3F)
        ) {
            OutlinedTextField(value = filmNameInput, onValueChange = { filmNameInput = it })
            Button(onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    repository.insertarPelicula((MiPelicula(nombre = filmNameInput)))
                }
            }) {
                Text(text = "SAVE")
            }
            Button(onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    repository.borrarMisPeliculas(filmList)
                }
            }) {
                Text(text = "ALL DELETE")
            }
        }
    }
}