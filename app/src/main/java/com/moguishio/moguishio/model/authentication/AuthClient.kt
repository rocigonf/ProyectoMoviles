package com.moguishio.moguishio.model.authentication

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthClient {
    @POST("auth")
    suspend fun login(@Body authRequest: AuthRequest): LoginResponse

    @POST("user")
    suspend fun signup(@Body authRequest: AuthRequest): SignUpResponse

    @POST("auth/refresh")
    suspend fun refreshToken(@Body refreshTokenRequest: TokenRequest): TokenResponse

    @GET("user/email/{emailInput}")
    suspend fun getUserData(): SignUpResponse
}