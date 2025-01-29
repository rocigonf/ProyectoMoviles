package com.moguishio.moguishio.model.authentication

import com.moguishio.moguishio.model.films.ClientePelicula
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAuthInstance {
    private const val BASE_URL = "http://10.0.2.2:8080/api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val clientePelicula: ClientePelicula by lazy {
        retrofit.create(ClientePelicula::class.java)
    }
}