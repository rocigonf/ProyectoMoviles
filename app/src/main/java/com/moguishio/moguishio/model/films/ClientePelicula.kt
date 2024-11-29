package com.moguishio.moguishio.model.films

import retrofit2.http.GET

interface ClientePelicula {
    @GET("/v3/7efd0923-583b-473e-83b3-cd050c75b793")
    suspend fun getFilms(): List<DatosPelicula>
}