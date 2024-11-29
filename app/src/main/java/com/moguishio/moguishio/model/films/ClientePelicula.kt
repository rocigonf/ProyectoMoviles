package com.moguishio.moguishio.model.films

import retrofit2.http.GET

interface ClientePelicula {
    @GET("/v3/731a11c6-ae32-4b77-adcb-b5eabb4cc652")
    suspend fun getFilms(): List<DatosPelicula>
}