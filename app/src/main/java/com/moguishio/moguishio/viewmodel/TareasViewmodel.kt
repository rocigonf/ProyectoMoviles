package com.moguishio.moguishio.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.moguishio.moguishio.AplicacionTareas
import com.moguishio.moguishio.model.tareas.MiPelicula
import com.moguishio.moguishio.model.tareas.RepositorioMisPeliculas
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TareasViewmodel(private val repositorioMisPeliculas: RepositorioMisPeliculas) : ViewModel() {
    fun getAll(): Flow<List<MiPelicula>>
            = repositorioMisPeliculas.getAll()

    fun getTotalNoVistas(): Flow<Int> = repositorioMisPeliculas.getTotalNoVistas()

    fun insertarPelicula(nombre: String) = viewModelScope.launch {
        repositorioMisPeliculas.insertarPelicula(MiPelicula(nombre = nombre, vista = false))
    }

    fun borrarTodasMisPeliculas(peliculas: List<MiPelicula>) = viewModelScope.launch {
        repositorioMisPeliculas.borrarMisPeliculas(peliculas)
    }

    fun borrarPelicula(pelicula: MiPelicula) = viewModelScope.launch {
        repositorioMisPeliculas.modificarPelicula(pelicula)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AplicacionTareas)
                TareasViewmodel(application.container.repositorioMisPeliculas)
            }
        }
    }
}