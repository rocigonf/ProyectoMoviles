package com.moguishio.moguishio.model.films

import retrofit2.http.GET

interface ClientePelicula {
    @GET("/v3/5a03a760-48dd-4b1f-b7f8-c0d3ae675ea6")
    suspend fun getFilms(): List<DatosPelicula>
}