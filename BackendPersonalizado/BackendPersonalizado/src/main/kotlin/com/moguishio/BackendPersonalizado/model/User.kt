package com.moguishio.BackendPersonalizado.model

import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class User(
    val id: Int,
    val email: String,
    val password: String,
    val role: Role
)

@Table("roles")
data class Role (
    val id: Int,
    val name : String
)