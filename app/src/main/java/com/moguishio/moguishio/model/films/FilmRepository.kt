package com.moguishio.moguishio.model.films

class FilmRepository {
    private val filmService = RetrofitInstance.clientePelicula

    suspend fun getFilms(): List<DatosPelicula> {
        return filmService.getFilms()
    }
}