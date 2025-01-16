package com.moguishio.BackendPersonalizado.controller.user

import com.moguishio.BackendPersonalizado.model.Role

data class UserRequest(
  val email: String,
  val password: String,
  val role: Role
)

