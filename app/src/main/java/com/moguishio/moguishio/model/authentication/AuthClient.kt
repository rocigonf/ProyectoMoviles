package com.moguishio.moguishio.model.authentication

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthClient {
    @POST("auth")
    suspend fun login(@Body authRequest: AuthRequest): Response<LoginResponse>

    @POST("user")
    suspend fun signup(@Body authRequest: AuthRequest): Response<SignUpResponse>

    @POST("auth/refresh")
    suspend fun refreshToken(@Body refreshToken: TokenRequest): Response<TokenResponse>

    @GET("user/email/")
    suspend fun getUserData(@Query("emailInput") emailInput: String): Response<SignUpResponse>
}