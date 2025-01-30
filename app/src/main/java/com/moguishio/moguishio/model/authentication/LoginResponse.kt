package com.moguishio.moguishio.model.authentication

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)
