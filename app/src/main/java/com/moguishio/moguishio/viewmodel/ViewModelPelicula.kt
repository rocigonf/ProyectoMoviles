package com.moguishio.moguishio.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moguishio.moguishio.model.films.DatosPelicula
import com.moguishio.moguishio.model.films.FilmRepository
import kotlinx.coroutines.launch

class ViewModelPelicula : ViewModel() {
    private val repository = FilmRepository()

    private val _films = MutableLiveData<List<DatosPelicula>>()
    val films: LiveData<List<DatosPelicula>> = _films

    fun fetchFilms() {
        viewModelScope.launch {
            try {
                val films = repository.getFilms()
                _films.value = films
            } catch (e: Exception) {
                Log.e("ERROR", "El error es: ${e.message}")
            }
        }
    }
}