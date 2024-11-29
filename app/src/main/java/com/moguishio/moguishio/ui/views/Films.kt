package com.moguishio.moguishio.ui.views

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.moguishio.moguishio.model.films.DatosPelicula
import com.moguishio.moguishio.ui.components.BotonVolver
import com.moguishio.moguishio.ui.components.EstablecerTexto
import com.moguishio.moguishio.ui.components.FilmItem
import com.moguishio.moguishio.viewmodel.ViewModelPelicula

@Composable
fun Films(navController: NavHostController, context: Context, viewModel: ViewModelPelicula) {
    val films by viewModel.films.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchFilms()
    }

    Column {
        if (films.isEmpty()) {
            // Muestra el indicador de cargando
            EstablecerTexto(text = "Loading...", textAlign = TextAlign.Center)
        } else {
            // Muestra la lista de columnas :D
            FilmList(films)
        }
    }

    BotonVolver(navController, context)
}

@Composable
fun FilmList(filmList: List<DatosPelicula>) {
    LazyColumn {
        itemsIndexed(items = filmList) { index, item ->
            FilmItem(film = item)
        }
    }
}