package com.moguishio.moguishio.model.films

import retrofit2.http.GET

interface ClientePelicula {
    @GET("/v3/41951a99-43aa-453f-b382-60eb72063c02")
    suspend fun getFilms(): List<DatosPelicula>
}