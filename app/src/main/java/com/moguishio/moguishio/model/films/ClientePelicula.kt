package com.moguishio.moguishio.model.films

import retrofit2.http.GET

interface ClientePelicula {
    @GET("/v3/e5a3f6bd-1314-4ec0-97cb-7d278c45ba29")
    suspend fun getFilms(): List<DatosPelicula>
}