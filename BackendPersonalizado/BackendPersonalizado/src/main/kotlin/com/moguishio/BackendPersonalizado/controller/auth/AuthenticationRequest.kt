package com.moguishio.BackendPersonalizado.controller.auth

data class AuthenticationRequest(
  val email: String,
  val password: String,
)