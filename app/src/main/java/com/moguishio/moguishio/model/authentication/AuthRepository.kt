package com.moguishio.moguishio.model.authentication

class AuthRepository {
    private val authClient = RetrofitAuthInstance.client

    suspend fun login(authRequest: AuthRequest): LoginResponse? {
        return authClient.login(authRequest).body()
    }

    suspend fun signup(authRequest: AuthRequest): SignUpResponse? {
        return authClient.signup(authRequest).body()
    }

    suspend fun refreshToken(refreshToken: TokenRequest): TokenResponse? {
        return authClient.refreshToken(refreshToken).body()
    }

    suspend fun getUserData(emailInput: String): SignUpResponse? {
        return authClient.getUserData(emailInput).body()
    }
}