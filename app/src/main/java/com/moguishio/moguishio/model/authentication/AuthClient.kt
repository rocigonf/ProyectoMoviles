package com.moguishio.moguishio.model.authentication

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthClient {
    @POST("auth")
    suspend fun login(@Body authRequest: AuthRequest): Response<LoginResponse>

    @POST("user")
    suspend fun signup(@Body authRequest: AuthRequest): Response<SignUpResponse>

    @POST("auth/refresh")
    suspend fun refreshToken(@Body refreshToken: TokenRequest): Response<TokenResponse>

    @GET("user/email/{emailInput}")
    suspend fun getUserData(@Header("Authorization") authorization: String, @Path("emailInput") emailInput: String): Response<SignUpResponse>
}