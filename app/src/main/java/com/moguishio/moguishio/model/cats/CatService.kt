package com.moguishio.moguishio.model.cats

import retrofit2.http.GET

interface CatService {
    @GET("cat")
    suspend fun getCats(): List<Cat>
}