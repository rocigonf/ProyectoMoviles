package com.moguishio.BackendPersonalizado.controller.auth

data class AuthenticationResponse(
  val accessToken: String,
  val refreshToken: String,
)