package com.moguishio.moguishio.model.films

import retrofit2.http.GET

interface ClientePelicula {
    @GET("films")
    suspend fun getFilms(): List<DatosPelicula>
}