package com.moguishio.moguishio.model.films

import retrofit2.http.GET

interface ClientePelicula {
    @GET("/v3/e25e8095-9132-4220-8007-414f5c783a4a")
    suspend fun getFilms(): List<DatosPelicula>
}