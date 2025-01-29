package com.moguishio.moguishio.model.authentication

import retrofit2.http.GET
import retrofit2.http.POST

interface AuthClient {
    @POST("auth")
    suspend fun login(): Unit

    @POST("user")
    suspend fun signup(): Unit

    @POST("auth/refresh")
    suspend fun refreshToken(): Unit

    @GET("user/email/{emailInput}")
    suspend fun getUserData(): Unit
}