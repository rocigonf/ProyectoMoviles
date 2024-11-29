package com.moguishio.moguishio.model.films

import retrofit2.http.GET

interface ClientePelicula {
    @GET("/v3/524a6928-c303-4b03-a923-22de056231f1")
    suspend fun getFilms(): List<DatosPelicula>
}